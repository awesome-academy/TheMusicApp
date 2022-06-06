package com.hungngo.themusicapp.data.model

import com.google.gson.annotations.SerializedName

data class Track (
    @SerializedName("album")
    var album: Album? = null,
    @SerializedName("artists")
    var artists: ArrayList<Artist>? = null,
    @SerializedName("disc_number")
    var discNumber: Int = 0,
    @SerializedName("duration_ms")
    var durationMs: Int = 0,
    @SerializedName("explicit")
    var explicit: Boolean = false,
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("is_local")
    var isLocal: Boolean = false,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("popularity")
    var popularity: Int = 0,
    @SerializedName("preview_url")
    var previewUrl: String? = null,
    @SerializedName("track_number")
    var trackNumber: Int = 0,
    @SerializedName("type")
    var type: String? = null
)
