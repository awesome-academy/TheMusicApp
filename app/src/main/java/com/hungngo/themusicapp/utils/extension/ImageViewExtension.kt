package com.hungngo.themusicapp.utils.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.hungngo.themusicapp.R

fun ImageView.loadImageWithUrl(url: String) {
    Glide.with(this)
        .load(url)
        .placeholder(R.drawable.bg_splashscreen)
        .into(this)
}
