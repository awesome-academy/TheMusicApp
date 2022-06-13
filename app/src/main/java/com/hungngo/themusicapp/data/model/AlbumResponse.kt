package com.hungngo.themusicapp.data.model

import com.google.gson.annotations.SerializedName

data class AlbumResponse (
    @SerializedName("albums")
    val albums: List<Album>? = null
)
