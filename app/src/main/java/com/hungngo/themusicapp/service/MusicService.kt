package com.hungngo.themusicapp.service

import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationManagerCompat
import com.hungngo.themusicapp.data.model.Track
import com.hungngo.themusicapp.service.notification.createNotification
import com.hungngo.themusicapp.service.notification.createNotificationChannel
import com.hungngo.themusicapp.ui.main.TrackState

class MusicService : Service() {

    private val mBinder = MyMusicBinder()
    private var musicPlayer: MediaPlayer? = MediaPlayer()
    private var listTracks: List<Track> = listOf()
    private var track: Track? = null
    private var currentTrackPosition = 0
    private var notificationManager: NotificationManagerCompat? = null
    private var serviceListener: TrackState? = null

    override fun onCreate() {
        super.onCreate()
        notificationManager = NotificationManagerCompat.from(this)
        createNotificationChannel(applicationContext, notificationManager)
    }

    override fun onBind(p0: Intent?): IBinder = mBinder

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        pushNotification()
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        if (musicPlayer != null) {
            musicPlayer?.stop()
            musicPlayer?.release()
            musicPlayer = null
        }
    }

    fun setListTracks(listTracks: List<Track>) {
        this.listTracks = listTracks
    }

    fun setTrackPosition(position: Int) {
        currentTrackPosition = position
    }

    fun getCurrentProgress() = musicPlayer?.currentPosition

    fun getDuration() = musicPlayer?.duration

    fun pushNotification() {
        notificationManager.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForeground(
                    FOREGROUND_NOTIFICATION_ID,
                    createNotification(applicationContext, track, musicPlayer?.isPlaying)
                )
            } else {
                this?.notify(
                    FOREGROUND_NOTIFICATION_ID,
                    createNotification(applicationContext, track, musicPlayer?.isPlaying)
                )
            }
        }
    }

    fun seekToPosition(position: Int?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            position?.let {
                musicPlayer?.seekTo(it.toLong(), MediaPlayer.SEEK_CLOSEST)
            }
        } else {
            position?.let { musicPlayer?.seekTo(it) }
        }
    }

    fun playTrack(position: Int) {
        musicPlayer?.apply {
            if (isPlaying) {
                stop()
            }

            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            val url = listTracks[position].previewUrl
            track = listTracks[position]

            reset()
            setDataSource(url)
            prepare()
            start()

            setOnCompletionListener {
                nextTrack()
                serviceListener?.playTrackFinish(currentTrackPosition)
            }

            pushNotification()
        }
    }

    fun isPlaying() = musicPlayer?.isPlaying

    fun pauseTrack() {
        musicPlayer?.apply {
            if (isPlaying) {
                pause()
            }
        }
        pushNotification()
    }

    fun nextTrack() {
        currentTrackPosition = (currentTrackPosition + 1) % (listTracks.size)
        playTrack(currentTrackPosition)
        serviceListener?.playTrackFinish(currentTrackPosition)
    }

    fun previousTrack() {
        currentTrackPosition = if (currentTrackPosition > 0) currentTrackPosition - 1 else listTracks.size - 1
        playTrack(currentTrackPosition)
        serviceListener?.playTrackFinish(currentTrackPosition)
    }

    fun resumeTrack() {
        musicPlayer?.currentPosition?.let {
            musicPlayer?.seekTo(it)
            musicPlayer?.start()
        }
        pushNotification()
    }

    fun setListener(serviceListener: TrackState) {
        this.serviceListener = serviceListener
    }

    fun getIntent() = Intent(applicationContext, MusicService::class.java)

    inner class MyMusicBinder : Binder() {
        fun getService(): MusicService = this@MusicService
    }

    companion object {
        const val FOREGROUND_NOTIFICATION_ID = 1
    }
}
