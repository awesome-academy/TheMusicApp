package com.hungngo.themusicapp.data.source

import com.hungngo.themusicapp.data.model.PlayListResponse

interface TrackDataSource {

    interface Remote {
        suspend fun getPlayListByID(playlistId: String) : PlayListResponse
    }
}
