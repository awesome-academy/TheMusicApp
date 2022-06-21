package com.hungngo.themusicapp.data.source

import com.hungngo.themusicapp.data.model.LyricsResponse

interface LyricDataSource {

    interface Remote {
        suspend fun getLyricsByID(id: String) : LyricsResponse
    }
}
