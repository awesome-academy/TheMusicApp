package com.hungngo.themusicapp.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

typealias ActivityInflate<T> = (LayoutInflater) -> T
abstract class BaseActivityViewBinding<B: ViewBinding>(private val inflate: ActivityInflate<B>) : AppCompatActivity() {

    private var _binding: B? = null
    protected val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = inflate(layoutInflater)
        setContentView(binding?.root)
        init()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    abstract fun init()
}
