package com.hungngo.themusicapp.ui.no_internet

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.navigation.fragment.findNavController
import com.hungngo.themusicapp.NetworkReceiver
import com.hungngo.themusicapp.NetworkUtils
import com.hungngo.themusicapp.OnNetworkChangeCallback
import com.hungngo.themusicapp.R
import com.hungngo.themusicapp.base.BaseFragmentViewBinding
import com.hungngo.themusicapp.databinding.FragmentNoInternetBinding

class NoInternetFragment :
    BaseFragmentViewBinding<FragmentNoInternetBinding>(FragmentNoInternetBinding::inflate),
    View.OnClickListener,
    OnNetworkChangeCallback {

    private var lisener: OnItemClick? = null
    private val networkReceiver = NetworkReceiver(this)
    private var isPausing = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        lisener = context as OnItemClick
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.registerReceiver(
            networkReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
        binding?.apply {
            buttonExit.setOnClickListener(this@NoInternetFragment)
            buttonConnectNetwork.setOnClickListener(this@NoInternetFragment)
            buttonLocal.setOnClickListener(this@NoInternetFragment)
        }
    }

    override fun onNetworkChange(isNetworkConnected: Boolean) {
        if (isNetworkConnected && !isPausing) {
            lisener?.onHandleNetworkEvent()
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.button_exit -> lisener?.onClickExit()
            R.id.button_connect_network -> startActivity(Intent(Settings.ACTION_SETTINGS))
            R.id.button_local -> findNavController().navigate(R.id.action_noInternetFragment_to_favoriteFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        if (NetworkUtils.isNetworkAvailable(context)) {
            lisener?.onHandleNetworkEvent()
        }
    }

    override fun onPause() {
        super.onPause()
        isPausing = true
    }

    override fun initView() {
    }

    override fun initData() {
    }

    override fun bindData() {
    }

    interface OnItemClick {
        fun onClickExit()
        fun onHandleNetworkEvent()
    }
}
