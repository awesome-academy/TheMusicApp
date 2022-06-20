package com.hungngo.themusicapp.data.repository

import com.hungngo.themusicapp.base.BaseRepository
import com.hungngo.themusicapp.data.LyricRepository
import com.hungngo.themusicapp.data.model.LyricsResponse
import com.hungngo.themusicapp.data.source.LyricDataSource
import com.hungngo.themusicapp.utils.DataResult

class LyricRepositoryImpl(
    private val remote: LyricDataSource.Remote
) : BaseRepository(), LyricRepository {

    override suspend fun getLyricsByID(id: String): DataResult<LyricsResponse> = withResultContext {
        remote.getLyricsByID(id)
    }
}
