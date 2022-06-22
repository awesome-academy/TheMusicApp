package com.hungngo.themusicapp.service.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.os.bundleOf
import androidx.media.app.NotificationCompat.MediaStyle
import com.hungngo.themusicapp.R
import com.hungngo.themusicapp.data.model.Album
import com.hungngo.themusicapp.data.model.Track
import com.hungngo.themusicapp.ui.main.MainActivity
import com.hungngo.themusicapp.utils.Constant
import java.io.Serializable
import kotlin.random.Random

private const val MEDIA_SESSION_TAG = "tag"

fun createNotificationChannel(context: Context, notificationManagerCompat: NotificationManagerCompat?) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationChannel = NotificationChannel(
            context.getString(R.string.msg_channel_id),
            context.getString(R.string.msg_channel_name),
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManagerCompat?.createNotificationChannel(notificationChannel)
    }
}

fun createNotification(context: Context, track: Track?, listTracks: List<Track>, isPlaying: Boolean?): Notification {
    val mediaSessionCompat = MediaSessionCompat(context, MEDIA_SESSION_TAG)
    val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.bg_notification_music)
    val intent = Intent(context, MainActivity::class.java).also {
        val bundle = bundleOf(
            Constant.BUNDLE_LIST_TRACKS to listTracks as Serializable,
            Constant.BUNDLE_TRACK_POS to track?.trackNumber
        )
        it.putExtras(bundle)
        it.action = Constant.ACTION_OPEN_ACTIVITY
    }
    val intentBack: PendingIntent =
        PendingIntent.getActivity(context, Random.nextInt(), intent, PendingIntent.FLAG_IMMUTABLE)

    return NotificationCompat.Builder(context, context.getString(R.string.msg_channel_id)).apply {
        setContentTitle(track?.name)
        setContentText(track?.artists?.first()?.name)
        setSmallIcon(R.drawable.ic_music)
        setLargeIcon(bitmap)
        setContentIntent(intentBack)
        addAction(
            R.drawable.ic_previous,
            context.getString(R.string.title_notification_action_previous),
            getPendingIntent(context, Constant.ACTION_PREVIOUS)
        )
        if (isPlaying == true) {
            addAction(
                R.drawable.ic_pause_40,
                context.getString(R.string.title_notification_action_pause),
                getPendingIntent(context, Constant.ACTION_PAUSE)
            )
        } else {
            addAction(
                R.drawable.ic_resume,
                context.getString(R.string.title_notification_action_resume),
                getPendingIntent(context, Constant.ACTION_RESUME)
            )
        }
        addAction(
            R.drawable.ic_next,
            context.getString(R.string.title_notification_action_next),
            getPendingIntent(context, Constant.ACTION_NEXT)
        )

        setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        setStyle(
            MediaStyle().run {
                setShowActionsInCompactView(1)
                setMediaSession(mediaSessionCompat.sessionToken)
            }
        )
    }.build()
}

fun getPendingIntent(context: Context, action: String): PendingIntent {
    Intent(context, MyReceiver::class.java).also {
        it.putExtra(Constant.EXTRA_MUSIC, action)
        it.action = action
        return PendingIntent.getBroadcast(
            context.applicationContext,
            Random.nextInt(),
            it,
            PendingIntent.FLAG_IMMUTABLE
        )
    }
}
