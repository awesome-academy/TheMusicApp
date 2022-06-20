package com.hungngo.themusicapp.ui.main

import android.app.Activity
import android.content.*
import android.os.Build
import android.os.IBinder
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.hungngo.themusicapp.R
import com.hungngo.themusicapp.base.BaseActivityViewBinding
import com.hungngo.themusicapp.data.model.Track
import com.hungngo.themusicapp.databinding.ActivityMainBinding
import com.hungngo.themusicapp.service.MusicService
import com.hungngo.themusicapp.ui.playlist.PlaylistFragment
import com.hungngo.themusicapp.utils.Constant
import com.hungngo.themusicapp.utils.extension.show
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivityViewBinding<ActivityMainBinding>(ActivityMainBinding::inflate),
    PlaylistFragment.PlayTrack,
    View.OnClickListener,
    TrackState {

    private val mainViewModel: MainViewModel by viewModel()
    private var musicService: MusicService? = null
    private var isConnected = false
    private var trackPosition = 0
    private var listTracks: List<Track> = listOf()
    private var bottomSheetPlayer: BottomSheetPlayer = BottomSheetPlayer.getInstance()

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            val myBinder = p1 as MusicService.MyMusicBinder
            musicService = myBinder.getService()
            musicService?.setListener(this@MainActivity)
            isConnected = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            isConnected = false
        }
    }

    private val musicReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.getStringExtra(getString(R.string.extra_action))) {
                Constant.ACTION_NEXT -> clickNextTrack()
                Constant.ACTION_PREVIOUS -> clickPreviousTrack()
                Constant.ACTION_PAUSE -> resumeOrPauseTrack()
                Constant.ACTION_RESUME -> resumeOrPauseTrack()
            }
        }
    }

    override fun init() {
        setUpFragment()

        setOnClick()

        observeItem()

        bindService()

        registerReceiver(musicReceiver, IntentFilter(getString(R.string.action_notification)))
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(musicService?.getIntent())
        if (isConnected) {
            unbindService(serviceConnection)
            isConnected = false
        }
        unregisterReceiver(musicReceiver)
    }

    override fun playTracks(trackPosition: Int, idTrack: String, idAlbum: String?) {
        this.trackPosition = trackPosition
        musicService?.setTrackPosition(trackPosition)
        mainViewModel.apply {
            idAlbum?.let { getAlbumByID(it) }
        }
        updateImageResumeOrPlay(true)
    }

    override fun playTrackFinish(position: Int) {
        trackPosition = position
        updateTrackView()
        bottomSheetPlayer.setTrack(listTracks[trackPosition])
        listTracks[trackPosition].id?.let { mainViewModel.getLyricsByID(it) }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.image_play_song -> {
                resumeOrPauseTrack()
            }
            R.id.image_next_song -> {
                clickNextTrack()
            }
            R.id.image_previous_song -> {
                clickPreviousTrack()
            }
            R.id.layout_player_controller -> {
                openBottomSheetPlayer()
            }
        }
    }

    private fun openBottomSheetPlayer() {
        musicService?.let { service -> bottomSheetPlayer.setService(service) }
        bottomSheetPlayer.also {
            it.setTrack(listTracks[trackPosition])
            it.show(supportFragmentManager, bottomSheetPlayer.tag)
        }
    }

    private fun setOnClick() {
        binding?.apply {
            imagePlaySong.setOnClickListener(this@MainActivity)
            imageNextSong.setOnClickListener(this@MainActivity)
            imagePreviousSong.setOnClickListener(this@MainActivity)
            layoutPlayerController.setOnClickListener(this@MainActivity)
        }
    }

    private fun setUpFragment() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding?.apply {
            setupWithNavController(bottomNavigationView, navController)
        }
    }

    private fun observeItem() {
        mainViewModel.apply {
            albums.observe(this@MainActivity) { albumResponse ->
                startService()
                listTracks = albumResponse?.albums?.first()?.tracks?.items?.toList() ?: emptyList()
                musicService?.setListTracks(listTracks)
                musicService?.playTrack(trackPosition)
                listTracks[trackPosition].id?.let { idTrack -> this.getLyricsByID(idTrack) }
                updateTrackView()
                updateProgress()
            }

            lyric.observe(this@MainActivity) {
                it.lyric?.let { it1 -> bottomSheetPlayer.setLyric(it1) }
            }
        }
    }

    private fun updateProgress() {
        val progressService: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
        progressService.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    if (musicService?.getCurrentProgress()!! < musicService?.getDuration()!!) {
                        bottomSheetPlayer.setProgress(musicService?.getCurrentProgress() ?: 0)
                    }
                }
            }

        }, FIRST_TIME_DELAY, TIME_DELAY, TimeUnit.SECONDS)
    }

    private fun clickNextTrack() {
        musicService?.apply {
            nextTrack()
            isPlaying()?.let { updateImageResumeOrPlay(it) }
        }
    }

    private fun clickPreviousTrack() {
        musicService?.apply {
            previousTrack()
            isPlaying()?.let { updateImageResumeOrPlay(it) }
        }
    }

    private fun bindService() {
        if (!isConnected) {
            Intent(this, MusicService::class.java).also {
                bindService(it, serviceConnection, Context.BIND_AUTO_CREATE)
            }
        }
    }

    private fun startService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) startForegroundService(musicService?.getIntent())
        else startService(musicService?.getIntent())
    }

    private fun resumeOrPauseTrack() {
        musicService?.apply {
            if (isPlaying() == true) {
                updateImageResumeOrPlay(false)
                pauseTrack()
            } else {
                updateImageResumeOrPlay(true)
                resumeTrack()
            }
        }
    }

    private fun updateImageResumeOrPlay(isPlaying: Boolean) {
        if (isPlaying) binding?.imagePlaySong?.setImageResource(R.drawable.ic_pause_40)
        else binding?.imagePlaySong?.setImageResource(R.drawable.ic_resume)
    }

    private fun updateTrackView() {
        binding?.apply {
            textSongName.text = listTracks[trackPosition].name
            textSongSinger.text = listTracks[trackPosition].artists?.first()?.name
            layoutPlayerController.show()
        }
    }

    companion object {
        const val FIRST_TIME_DELAY = 0L
        const val TIME_DELAY = 1L
        fun getIntent(startActivity: Activity) = Intent(startActivity, MainActivity::class.java)
    }
}
