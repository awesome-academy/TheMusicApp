package com.hungngo.themusicapp.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<B : ViewDataBinding>(@LayoutRes private val layoutResId: Int) : AppCompatActivity() {
    private var _binding: B? = null
    protected val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        _binding = DataBindingUtil.setContentView(this, layoutResId)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
