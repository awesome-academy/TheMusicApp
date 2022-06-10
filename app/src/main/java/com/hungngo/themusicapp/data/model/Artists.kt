package com.hungngo.themusicapp.data.model

import com.google.gson.annotations.SerializedName

data class Artists(
    @SerializedName("items")
    val items: List<ItemArtist>
)
