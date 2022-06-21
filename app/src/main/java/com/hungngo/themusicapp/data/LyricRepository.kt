package com.hungngo.themusicapp.data

import com.hungngo.themusicapp.data.model.LyricsResponse
import com.hungngo.themusicapp.utils.DataResult

interface LyricRepository {

    suspend fun getLyricsByID(id: String) : DataResult<LyricsResponse>
}
