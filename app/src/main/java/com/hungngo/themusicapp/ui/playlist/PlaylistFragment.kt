package com.hungngo.themusicapp.ui.playlist

import android.content.Context
import androidx.navigation.fragment.navArgs
import com.hungngo.themusicapp.R
import com.hungngo.themusicapp.base.BaseFragmentViewBinding
import com.hungngo.themusicapp.data.model.Album
import com.hungngo.themusicapp.data.model.Track
import com.hungngo.themusicapp.databinding.FragmentPlaylistBinding
import com.hungngo.themusicapp.utils.extension.hide
import com.hungngo.themusicapp.utils.extension.loadImageWithUrl
import com.hungngo.themusicapp.utils.extension.show
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistFragment :
    BaseFragmentViewBinding<FragmentPlaylistBinding>(FragmentPlaylistBinding::inflate) {

    private val albumAdapter by lazy { AlbumAdapter() }
    private val playlistViewModel: PlaylistViewModel by viewModel()
    private val args: PlaylistFragmentArgs by navArgs()
    private val idTrack by lazy { args.idTrack }
    private val idAlbum by lazy { args.idAlbum }
    private var trackPosition = 0
    private var playTrackListener: PlayTrack? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        playTrackListener = context as PlayTrack
    }

    override fun initView() {
        binding?.apply {
            rcvTrackInAlbum.adapter = albumAdapter
        }
    }

    override fun initData() {
        getAlbumByID(idAlbum)
    }

    override fun bindData() {
        playlistViewModel.apply {
            isLoading.observe(viewLifecycleOwner) {
                if (it) showProgressBar() else hideProgressBar()
            }
            album.observe(viewLifecycleOwner) {
                setDataToView(it.albums?.first())
                checkTrackPositionInAlbum(it.albums?.first()?.tracks?.items)
                albumAdapter.submitList(it.albums?.first()?.tracks?.items)
            }
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
        playTrackListener?.playTracks(trackPosition, idTrack, idAlbum)
        binding?.fabButtonPlay?.setImageResource(R.drawable.ic_pause_40)
    }

    private fun showProgressBar() {
        binding?.progressPlaylist?.show()
    }

    private fun hideProgressBar() {
        binding?.progressPlaylist?.hide()
    }

    interface PlayTrack {
        fun playTracks(trackPosition: Int, idTrack: String, idAlbum: String?)
    }
}
