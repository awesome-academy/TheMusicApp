package com.hungngo.themusicapp.data.model

import com.google.gson.annotations.SerializedName

data class Visual(
    @SerializedName("avatarImage")
    val avatarImage: CoverArt? = null
)
