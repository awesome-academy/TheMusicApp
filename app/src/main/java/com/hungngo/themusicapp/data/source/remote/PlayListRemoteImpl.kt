package com.hungngo.themusicapp.data.source.remote

import com.hungngo.themusicapp.data.model.PlayListResponse
import com.hungngo.themusicapp.data.source.PlayListDataSource
import com.hungngo.themusicapp.data.source.remote.api.ApiService

class PlayListRemoteImpl(private val apiService: ApiService) : PlayListDataSource.Remote {

    override suspend fun getPlayListByID(playlistId: String): PlayListResponse {
        return apiService.getPlayListByID(playlistId)
    }
}
