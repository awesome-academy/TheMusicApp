package com.hungngo.themusicapp.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.hungngo.themusicapp.R

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(view.context).load(url).placeholder(R.drawable.ic_launch).into(view)
    }
}
