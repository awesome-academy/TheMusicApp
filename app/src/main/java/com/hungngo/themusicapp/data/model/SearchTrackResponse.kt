package com.hungngo.themusicapp.data.model

import com.google.gson.annotations.SerializedName

data class SearchTrackResponse(
    @SerializedName("query")
    var query: String? = null,
    @SerializedName("tracks")
    var tracks: TracksInSearch? = null
)
