package com.hungngo.themusicapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Lyric(
    @SerializedName("lines")
    var lines: List<Line>? = null
) : Parcelable
