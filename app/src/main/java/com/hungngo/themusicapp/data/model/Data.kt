package com.hungngo.themusicapp.data.model

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("albumOfTrack")
    val albumOfTrack: Album? = null,
    @SerializedName("artists")
    val artists: Artists? = null,
    @SerializedName("duration")
    val duration: Duration? = null,
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("uri")
    val uri: String? = null
)
