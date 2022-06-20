package com.hungngo.themusicapp.service.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.hungngo.themusicapp.R
import com.hungngo.themusicapp.utils.Constant

class MyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val actionMusic = intent?.getStringExtra(Constant.EXTRA_MUSIC)
        context?.sendBroadcast(
            Intent(context.getString(R.string.action_notification)).also {
                it.putExtra(context.getString(R.string.extra_action), actionMusic)
            }
        )
    }
}
