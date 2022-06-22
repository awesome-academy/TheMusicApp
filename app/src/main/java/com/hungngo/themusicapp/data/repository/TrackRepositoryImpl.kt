package com.hungngo.themusicapp.data.repository

import com.hungngo.themusicapp.base.BaseRepository
import com.hungngo.themusicapp.data.TrackRepository
import com.hungngo.themusicapp.data.model.Track
import com.hungngo.themusicapp.data.model.TrackRespond
import com.hungngo.themusicapp.data.source.TrackDataSource
import com.hungngo.themusicapp.utils.DataResult
import com.hungngo.themusicapp.utils.dispatchers.BaseDispatcherProvider
import kotlinx.coroutines.withContext
import org.koin.core.component.get

class TrackRepositoryImpl(
    val remote: TrackDataSource.Remote,
    val local: TrackDataSource.Local
) : BaseRepository(), TrackRepository {

    private val dispatchersProvider = get<BaseDispatcherProvider>()

    override suspend fun getTrackByID(idTrack: String): DataResult<TrackRespond> =
        withResultContext {
            remote.getTrackByID(idTrack)
        }

    override suspend fun insertTrack(track: Track): Unit {
        withContext(dispatchersProvider.io()) {
            local.insertTrack(track)
        }
    }

    override suspend fun getAllTrack(): DataResult<List<Track>> = withResultContext {
        local.getAllTrack()
    }

    override suspend fun deleteTrack(track: Track): DataResult<Unit> = withResultContext {
        local.deleteTrack(track)
    }

    override suspend fun isFavorite(idTrack: String): Boolean =
        withContext(dispatchersProvider.io()) {
            local.isFavorite(idTrack)
        }
}
