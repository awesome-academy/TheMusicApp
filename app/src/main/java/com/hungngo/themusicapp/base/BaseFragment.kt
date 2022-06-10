package com.hungngo.themusicapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<out B : ViewDataBinding>(@LayoutRes private val layoutResId: Int) :
    Fragment() {

    private var _binding: B? = null
    protected val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)

        init()

        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    abstract fun init()
}
