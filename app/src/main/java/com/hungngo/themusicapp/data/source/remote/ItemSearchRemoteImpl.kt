package com.hungngo.themusicapp.data.source.remote

import com.hungngo.themusicapp.data.model.ItemsSearchResponse
import com.hungngo.themusicapp.data.source.ItemSearchDataSource
import com.hungngo.themusicapp.data.source.remote.api.ApiService

class ItemSearchRemoteImpl(private val apiService: ApiService) : ItemSearchDataSource.Remote {

    override suspend fun searchArtist(query: String, type: String): ItemsSearchResponse {
        return apiService.searchItems(query, type)
    }
}
