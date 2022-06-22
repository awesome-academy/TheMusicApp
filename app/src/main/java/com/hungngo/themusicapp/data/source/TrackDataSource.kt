package com.hungngo.themusicapp.data.source

import com.hungngo.themusicapp.data.model.Track
import com.hungngo.themusicapp.data.model.TrackRespond

interface TrackDataSource {

    interface Local {
        suspend fun insertTrack(track: Track)
        suspend fun deleteTrack(track: Track)
        suspend fun getAllTrack(): List<Track>
        suspend fun isFavorite(idTrack: String) : Boolean
    }

    interface Remote {
        suspend fun getTrackByID(idTrack: String): TrackRespond
    }
}
