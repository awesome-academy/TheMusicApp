package com.hungngo.themusicapp.data.source.remote.api

import com.hungngo.themusicapp.data.model.*
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/playlist/")
    suspend fun getPlayListByID(@Query("id") playListID: String): PlayListResponse

    @GET("/search/")
    suspend fun searchItems(
        @Query("q") searchQuery: String,
        @Query("type") searchType: String
    ): ItemsSearchResponse

    @GET("/albums/")
    suspend fun getAlbumByID(
        @Query("ids") idAlbum: String
    ): AlbumResponse

    @GET("/tracks/")
    suspend fun getTrackByID(
        @Query("ids") idTrack: String
    ): TrackRespond

    @GET("/track_lyrics/")
    suspend fun getLyricsByID(
        @Query("id") idTrack: String
    ): LyricsResponse
}
