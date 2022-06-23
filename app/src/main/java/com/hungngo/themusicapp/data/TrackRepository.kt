package com.hungngo.themusicapp.data

import com.hungngo.themusicapp.data.model.Track
import com.hungngo.themusicapp.data.model.TrackRespond
import com.hungngo.themusicapp.utils.DataResult

interface TrackRepository {

    suspend fun getTrackByID(idTrack: String) : DataResult<TrackRespond>

    suspend fun insertTrack(track: Track)

    suspend fun getAllTrack() : DataResult<List<Track>>

    suspend fun deleteTrack(track: Track) : DataResult<Unit>

    suspend fun isFavorite(idTrack: String) : Boolean
}
