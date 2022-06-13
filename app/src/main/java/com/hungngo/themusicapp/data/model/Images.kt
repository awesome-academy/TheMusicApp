package com.hungngo.themusicapp.data.model

import com.google.gson.annotations.SerializedName

data class Images(
    @SerializedName("items")
    val items: List<CoverArt>? = null
)
