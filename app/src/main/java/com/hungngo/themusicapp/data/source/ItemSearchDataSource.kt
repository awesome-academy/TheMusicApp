package com.hungngo.themusicapp.data.source

import com.hungngo.themusicapp.data.model.ItemsSearchResponse

interface ItemSearchDataSource {

    interface Remote {
        suspend fun searchArtist(query: String, type: String) : ItemsSearchResponse
    }
}
