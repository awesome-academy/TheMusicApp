package com.hungngo.themusicapp.ui.main

import android.Manifest
import android.app.Activity
import android.app.DownloadManager
import android.content.*
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.IBinder
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.hungngo.themusicapp.NetworkReceiver
import com.hungngo.themusicapp.NetworkUtils
import com.hungngo.themusicapp.OnNetworkChangeCallback
import com.hungngo.themusicapp.R
import com.hungngo.themusicapp.base.BaseActivityViewBinding
import com.hungngo.themusicapp.data.model.Track
import com.hungngo.themusicapp.databinding.ActivityMainBinding
import com.hungngo.themusicapp.service.MusicService
import com.hungngo.themusicapp.ui.favorite.FavoriteFragment
import com.hungngo.themusicapp.ui.main.bottom_sheet_player.BottomSheetPlayer
import com.hungngo.themusicapp.ui.no_internet.NoInternetFragment
import com.hungngo.themusicapp.ui.playlist.PlaylistFragment
import com.hungngo.themusicapp.utils.Constant
import com.hungngo.themusicapp.utils.extension.hide
import com.hungngo.themusicapp.utils.extension.show
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivityViewBinding<ActivityMainBinding>(ActivityMainBinding::inflate),
    PlaylistFragment.PlayTrack,
    View.OnClickListener,
    TrackState,
    BottomSheetPlayer.DownLoadTrack,
    NoInternetFragment.OnItemClick,
    OnNetworkChangeCallback,
    FavoriteFragment.OnPlayLocal {

    private val mainViewModel: MainViewModel by viewModel()
    private var musicService: MusicService? = null
    private var isConnected = false
    private var trackPosition = -1
    private var listTracks: List<Track> = listOf()
    private var bottomSheetPlayer: BottomSheetPlayer = BottomSheetPlayer.getInstance()
    private var track: Track? = null
    private val networkReceiver = NetworkReceiver(this)

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

        bottomSheetPlayer.setListener(this)

        registerReceiver(networkReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

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
        unregisterReceiver(networkReceiver)
    }

    override fun playTracks(trackPosition: Int, idAlbum: String?) {
        this.trackPosition = trackPosition
        musicService?.setTrackPosition(trackPosition)
        mainViewModel.apply {
            idAlbum?.let { getAlbumByID(it) }
        }
        updateImageResumeOrPlay(true)
    }

    override fun clickFavorite(track: Track?) {
        track?.let { checkPermission(it) }
    }

    override fun playTrackFinish(position: Int) {
        trackPosition = position
        bottomSheetPlayer.checkFavorite(mainViewModel.isFavorite(listTracks[trackPosition].id))
        updateTrackView()
        bottomSheetPlayer.setTrack(listTracks[trackPosition])
        listTracks[trackPosition].id.let { mainViewModel.getLyricsByID(it) }
    }

    override fun checkPermission(track: Track) {
        this.track = track
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED
            ) {
                startDownload(track)
            } else {
                val permission = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permission, REQUEST_PERMISSION_CODE)
            }
        } else {
            startDownload(track)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults.first() == PackageManager.PERMISSION_GRANTED) {
            track?.let { startDownload(it) }
        } else {
            Toast.makeText(this, R.string.msg_permission_deny, Toast.LENGTH_SHORT).show()
        }
    }

    override fun playLocalTrack(trackPosition: Int, listTracks: List<Track>) {
        this.trackPosition = trackPosition
        this.listTracks = listTracks
        startService()
        musicService?.setTrackPosition(trackPosition)
        musicService?.setListTracks(listTracks)
        musicService?.playTrack(trackPosition)
        updateTrackView()
        updateImageResumeOrPlay(true)
        bottomSheetPlayer.checkFavorite(true)
        bottomSheetPlayer.setTrack(listTracks[trackPosition])
        updateProgress()
        binding?.layoutPlayerController?.isClickable = false
    }

    override fun onDeleteTrack(track: Track) {
        track.previewUrl?.let { File(it).delete() }
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

    private fun startDownload(track: Track) {
        val request = DownloadManager.Request(Uri.parse(track.previewUrl))
        request.apply {
            setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
            setTitle(R.string.title_download)
            setDescription(resources.getString(R.string.msg_downloading))

            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, track.name)
        }

        val downLoadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downLoadManager.enqueue(request)

        track.previewUrl =
            "${Environment.getExternalStoragePublicDirectory(PATH_TYPE)}/${track.name}$FILE_POSTFIX"
        track.artistName = track.artists?.first()?.name
        mainViewModel.insertTrack(track)
    }

    private fun openBottomSheetPlayer() {
        bottomSheetPlayer.checkFavorite(mainViewModel.isFavorite(listTracks[trackPosition].id))
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

        if (!NetworkUtils.isNetworkAvailable(this)) {
            binding?.bottomNavigationView?.visibility = View.GONE
            navController.apply {
                popBackStack()
                navigate(R.id.noInternetFragment)
            }
        }
    }

    private fun observeItem() {
        mainViewModel.apply {
            albums.observe(this@MainActivity) { albumResponse ->
                startService()
                listTracks = albumResponse?.albums?.first()?.tracks?.items?.toList() ?: emptyList()
                albumResponse?.albums?.first()?.id?.let { musicService?.setIdAlbum(it) }
                musicService?.apply {
                    setListTracks(listTracks)
                    playTrack(trackPosition)
                }
                listTracks[trackPosition].id.let { idTrack -> this.getLyricsByID(idTrack) }
                updateTrackView()
                updateProgress()
                bottomSheetPlayer.checkFavorite(mainViewModel.isFavorite(listTracks[trackPosition].id))
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
        Intent(this, MusicService::class.java).also {
            bindService(it, serviceConnection, Context.BIND_AUTO_CREATE)
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
                bottomSheetPlayer.updateButtonPauseOrResume(false)
            } else {
                updateImageResumeOrPlay(true)
                resumeTrack()
                bottomSheetPlayer.updateButtonPauseOrResume(true)
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
            textSongSinger.text = if (listTracks[trackPosition].artists?.first()?.name != null) {
                listTracks[trackPosition].artists?.first()?.name
            } else {
                listTracks[trackPosition].artistName
            }
            layoutPlayerController.apply {
                isClickable = true
                show()
            }

        }
    }

    override fun onClickExit() {
        moveTaskToBack(true)
        finish()
    }

    override fun onHandleNetworkEvent() {
        supportFragmentManager.apply {
            if (backStackEntryCount >= 1) popBackStack()
            else Intent(this@MainActivity, MainActivity::class.java).also {
                it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(it)
            }
        }
        binding?.apply {
            bottomNavigationView.show()
            layoutPlayerController.show()
        }

    }

    override fun onNetworkChange(isNetworkConnected: Boolean) {
        if (!isNetworkConnected) {
            binding?.apply {
                bottomNavigationView.hide()
                layoutPlayerController.hide()
            }
            findNavController(R.id.nav_host_fragment).apply {
                popBackStack()
                navigate(R.id.noInternetFragment)
            }
        }
    }

    companion object {
        const val FILE_POSTFIX = ".mp3"
        const val PATH_TYPE = "Download"
        const val REQUEST_PERMISSION_CODE = 100
        const val FIRST_TIME_DELAY = 0L
        const val TIME_DELAY = 1L
        fun getIntent(startActivity: Activity) = Intent(startActivity, MainActivity::class.java)
    }
}
