package com.hungngo.themusicapp.data.source.remote

import com.hungngo.themusicapp.data.model.TrackRespond
import com.hungngo.themusicapp.data.source.TrackDataSource
import com.hungngo.themusicapp.data.source.remote.api.ApiService

class TrackRemoteImpl(private val apiService: ApiService) : TrackDataSource.Remote {

    override suspend fun getTrackByID(idTrack: String): TrackRespond {
        return apiService.getTrackByID(idTrack)
    }
}
