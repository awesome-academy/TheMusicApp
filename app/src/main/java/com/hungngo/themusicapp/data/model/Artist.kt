package com.hungngo.themusicapp.data.model

import com.google.gson.annotations.SerializedName

class Artist(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("uri")
    val uri: String? = null
)
