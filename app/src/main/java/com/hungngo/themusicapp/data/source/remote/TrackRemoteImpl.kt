package com.hungngo.themusicapp.data.source.remote

import com.hungngo.themusicapp.data.model.SearchTrackResponse
import com.hungngo.themusicapp.data.source.TrackDataSource
import com.hungngo.themusicapp.data.source.remote.api.ApiService

class TrackRemoteImpl(private val apiService: ApiService) : TrackDataSource.Remote {

    override suspend fun searchTrack(query: String, type: String): SearchTrackResponse {
        return apiService.searchItem(query, type)
    }
}
