package com.hungngo.themusicapp.data.model

import com.google.gson.annotations.SerializedName

data class PagingInfo(
    @SerializedName("limit")
    val limit: Int? = 0
)
