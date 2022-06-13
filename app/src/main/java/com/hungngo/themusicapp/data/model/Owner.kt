package com.hungngo.themusicapp.data.model

import com.google.gson.annotations.SerializedName

data class Owner(
    @SerializedName("display_name", alternate = ["name"])
    val displayName: String? = null,
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("uri")
    val uri: String? = null
)
