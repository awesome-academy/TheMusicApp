package com.hungngo.themusicapp.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

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
    val uri: String? = null,
    @SerializedName("profile")
    val profile: Profile? = null,
    @SerializedName("visuals")
    val visual: Visual? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("owner")
    val owner: Owner? = null,
    @SerializedName("images")
    val images: Images? = null
) : Serializable
