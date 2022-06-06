package com.hungngo.themusicapp.data.model

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("height")
    val height: Int? = null,
    @SerializedName("url")
    val url: String? = null,
    @SerializedName("width")
    val width: Int? = null
)
