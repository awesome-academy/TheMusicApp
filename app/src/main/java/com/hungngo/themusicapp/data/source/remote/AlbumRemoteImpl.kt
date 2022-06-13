package com.hungngo.themusicapp.data.source.remote

import com.hungngo.themusicapp.data.model.AlbumResponse
import com.hungngo.themusicapp.data.source.AlbumDataSource
import com.hungngo.themusicapp.data.source.remote.api.ApiService

class AlbumRemoteImpl(private val apiService: ApiService): AlbumDataSource.Remote {

    override suspend fun getAlbumByID(id: String): AlbumResponse {
        return apiService.getAlbumByID(id)
    }
}
