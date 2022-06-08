package com.hungngo.themusicapp.data.model

import com.google.gson.annotations.SerializedName

data class PlayListResponse(
    @SerializedName("collaborative")
    val collaborative: Boolean? = false,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("followers")
    val followers: Followers? = null,
    @SerializedName("images")
    val images: List<Image>? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("owner")
    val owner: Owner? = null,
    @SerializedName("public")
    val public: Boolean? = false,
    @SerializedName("tracks")
    val tracks: Tracks? = null,
    @SerializedName("uri")
    val uri: String? = null
)
