package com.hungngo.themusicapp.data.model

import com.google.gson.annotations.SerializedName

data class Duration(
    @SerializedName("totalMilliseconds")
    val totalMilliseconds: Int
)
