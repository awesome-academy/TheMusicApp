package com.hungngo.themusicapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NetworkReceiver(private val networkCallback: OnNetworkChangeCallback) : BroadcastReceiver() {

    override fun onReceive(p0: Context?, p1: Intent?) {
        networkCallback.onNetworkChange(NetworkUtils.isNetworkAvailable(p0))
    }
}
