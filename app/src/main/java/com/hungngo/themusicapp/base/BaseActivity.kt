package com.hungngo.themusicapp.base

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<B : ViewDataBinding>(@LayoutRes private val layoutResId: Int) :
    AppCompatActivity() {
    private var _binding: B? = null
    val binding get() = _binding

    protected fun bindView(layoutId: Int) {
        _binding = DataBindingUtil.setContentView(this, layoutId)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
