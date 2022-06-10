package com.hungngo.themusicapp.data.source.remote.api

import com.hungngo.themusicapp.data.model.PlayListResponse
import com.hungngo.themusicapp.data.model.SearchTrackResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/playlist/")
    suspend fun getPlayListByID(@Query("id") playListID: String): PlayListResponse

    @GET("/search/")
    suspend fun searchItem(
        @Query("q") searchQuery: String,
        @Query("type") searchType: String
    ): SearchTrackResponse
}
