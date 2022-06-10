package com.hungngo.themusicapp.data.model

import com.google.gson.annotations.SerializedName

data class ItemArtist(
    @SerializedName("profile")
    val profile: Profile,
    @SerializedName("uri")
    val uri: String
)
