package com.hungngo.themusicapp.data.model

import com.google.gson.annotations.SerializedName

data class CoverArt(
    @SerializedName("sources")
    val sources: List<Image>
)
