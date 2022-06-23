package com.hungngo.themusicapp.ui.playlist

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.navigation.fragment.navArgs
import com.hungngo.themusicapp.R
import com.hungngo.themusicapp.base.BaseFragmentViewBinding
import com.hungngo.themusicapp.data.model.Album
import com.hungngo.themusicapp.data.model.Track
import com.hungngo.themusicapp.databinding.FragmentPlaylistBinding
import com.hungngo.themusicapp.service.MusicService
import com.hungngo.themusicapp.utils.Constant.INDEX_SIZE_DIFFERENT
import com.hungngo.themusicapp.utils.extension.hide
import com.hungngo.themusicapp.utils.extension.loadImageWithUrl
import com.hungngo.themusicapp.utils.extension.show
import com.hungngo.themusicapp.utils.extension.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistFragment :
    BaseFragmentViewBinding<FragmentPlaylistBinding>(FragmentPlaylistBinding::inflate),
    AlbumAdapter.OnItemClick,
    MusicService.UpdatePlaylistFragment {

    private val albumAdapter by lazy { AlbumAdapter() }
    private val playlistViewModel: PlaylistViewModel by viewModel()
    private val args: PlaylistFragmentArgs by navArgs()
    private val idTrack by lazy { args.idTrack }
    private val idAlbum by lazy { args.idAlbum }
    private var trackPosition = 0
    private var playTrackListener: PlayTrack? = null
    private var listTracks: List<Track>? = null
    private var track: Track? = null
    private var musicService: MusicService? = null
    private var isConnected = false

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            val myBinder = p1 as MusicService.MyMusicBinder
            musicService = myBinder.getService()
            musicService?.setPlaylistListener(this@PlaylistFragment)
            isConnected = true
            checkIsPlaying()
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            isConnected = false
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        playTrackListener = context as PlayTrack
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isConnected) {
            context?.unbindService(serviceConnection)
            isConnected = false
        }
    }

    override fun initView() {
        albumAdapter.setListener(this)
        binding?.apply {
            rcvTrackInAlbum.adapter = albumAdapter
        }
    }

    override fun initData() {
        if (!isConnected) {
            Intent(context, MusicService::class.java).also {
                context?.bindService(it, serviceConnection, Context.BIND_AUTO_CREATE)
            }
        }
        getAlbumByID(idAlbum)
    }

    override fun bindData() {
        playlistViewModel.apply {
            isLoading.observe(viewLifecycleOwner) {
                if (it) showProgressBar() else hideProgressBar()
            }
            album.observe(viewLifecycleOwner) {
                listTracks = it.albums?.first()?.tracks?.items
                setDataToView(it.albums?.first())
                checkTrackPositionInAlbum(it.albums?.first()?.tracks?.items)
                listTracks?.forEachIndexed { index, track ->
                    track.isFavorite = playlistViewModel.isFavorite(track)
                }
                albumAdapter.submitList(listTracks)
            }
        }
    }

    override fun onClickTrack(track: Track?) {
        if (track?.trackNumber != null) {
            playTrackListener?.playTracks(track.trackNumber.minus(INDEX_SIZE_DIFFERENT), idAlbum)
            binding?.fabButtonPlay?.setImageResource(R.drawable.ic_pause_40)
        }
    }

    override fun onClickFavorite(track: Track?, position: Int) {
        if (track?.isFavorite == true) context?.showToast(resources.getString(R.string.msg_track_downloaded))
        else playTrackListener?.clickFavorite(track)
    }

    override fun updateState(isPlaying: Boolean) {
        binding?.fabButtonPlay?.setImageResource(if (isPlaying) R.drawable.ic_pause_40 else R.drawable.ic_resume)
    }

    private fun checkIsPlaying() {
        binding?.apply {
            fabButtonPlay.setImageResource(
                if (musicService?.isPlaying() == true) R.drawable.ic_pause_40 else R.drawable.ic_resume
            )
        }
    }

    private fun checkTrackPositionInAlbum(trackList: List<Track>?) {
        trackList?.forEachIndexed { index, track ->
            if (track.id == idTrack) {
                trackPosition = index
            }
        }
    }

    private fun setDataToView(album: Album?) {
        binding?.apply {
            album?.images?.first()?.url?.let { imageAlbum.loadImageWithUrl(it) }
            textAlbum.text = album?.name
            textArtist.text = album?.artists?.first()?.name
            fabButtonPlay.setOnClickListener { playTracks() }
        }
    }

    private fun getAlbumByID(id: String?) {
        if (id != null) {
            playlistViewModel.getAlbumByID(id)
        }
    }

    private fun playTracks() {
        playTrackListener?.playTracks(trackPosition, idAlbum)
        binding?.fabButtonPlay?.setImageResource(R.drawable.ic_pause_40)
    }

    private fun showProgressBar() {
        binding?.progressPlaylist?.show()
    }

    private fun hideProgressBar() {
        binding?.progressPlaylist?.hide()
    }

    interface PlayTrack {
        fun playTracks(trackPosition: Int, idAlbum: String?)
        fun clickFavorite(track: Track?)
    }
}
