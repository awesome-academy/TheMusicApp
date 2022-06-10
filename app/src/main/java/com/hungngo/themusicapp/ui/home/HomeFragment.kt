package com.hungngo.themusicapp.ui.home

import android.os.Bundle
import android.view.View
import com.hungngo.themusicapp.R
import com.hungngo.themusicapp.base.BaseFragment
import com.hungngo.themusicapp.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun init() {

    }
}
