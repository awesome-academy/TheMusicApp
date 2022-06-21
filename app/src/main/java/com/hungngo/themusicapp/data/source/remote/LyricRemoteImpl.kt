package com.hungngo.themusicapp.data.source.remote

import com.hungngo.themusicapp.data.model.LyricsResponse
import com.hungngo.themusicapp.data.source.LyricDataSource
import com.hungngo.themusicapp.data.source.remote.api.ApiService

class LyricRemoteImpl(private val apiService: ApiService): LyricDataSource.Remote {

    override suspend fun getLyricsByID(id: String): LyricsResponse {
        return apiService.getLyricsByID(id)
    }
}
