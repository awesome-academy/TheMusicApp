package com.hungngo.themusicapp.data.model

import com.google.gson.annotations.SerializedName

data class ItemsSearchResponse(
    @SerializedName("query")
    var query: String? = null,
    @SerializedName("tracks", alternate = ["artists", "playlists"])
    var itemsInSearch: ItemsInSearch? = null
)
