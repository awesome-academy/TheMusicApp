package com.hungngo.themusicapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class LyricsResponse(
    @SerializedName("lyrics")
    var lyric: Lyric? = null
) : Parcelable
