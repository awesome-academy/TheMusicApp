package com.hungngo.themusicapp.data.source

import com.hungngo.themusicapp.data.model.PlayListResponse

interface PlayListDataSource {

    interface Remote {
        suspend fun getPlayListByID(playlistId: String) : PlayListResponse
    }
}
