package com.hungngo.themusicapp.data.repository

import com.hungngo.themusicapp.base.BaseRepository
import com.hungngo.themusicapp.data.TrackRepository
import com.hungngo.themusicapp.data.model.TrackRespond
import com.hungngo.themusicapp.data.source.TrackDataSource
import com.hungngo.themusicapp.utils.DataResult

class TrackRepositoryImpl(
    val remote: TrackDataSource.Remote
) : BaseRepository(), TrackRepository {

    override suspend fun getTrackByID(idTrack: String): DataResult<TrackRespond> = withResultContext {
        remote.getTrackByID(idTrack)
    }
}
