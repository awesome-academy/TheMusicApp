package com.hungngo.themusicapp.data.repository

import com.hungngo.themusicapp.base.BaseRepository
import com.hungngo.themusicapp.data.TrackRepository
import com.hungngo.themusicapp.data.source.TrackDataSource

class TrackRepositoryImpl(
    val remote: TrackDataSource.Remote
) : BaseRepository(), TrackRepository {

    override suspend fun searchItem(query: String, type: String) = withResultContext {
        remote.searchTrack(query, type)
    }
}
