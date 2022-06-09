package com.hungngo.themusicapp.data.model

import com.google.gson.annotations.SerializedName

data class Tracks(
    @SerializedName("items")
    val items: List<Track>? = null,
    @SerializedName("total")
    val total: Int? = null
)
