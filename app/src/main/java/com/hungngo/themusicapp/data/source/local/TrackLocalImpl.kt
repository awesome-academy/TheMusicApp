package com.hungngo.themusicapp.data.source.local

import com.hungngo.themusicapp.data.model.Track
import com.hungngo.themusicapp.data.source.TrackDataSource
import com.hungngo.themusicapp.data.source.local.room.SongDAO

class TrackLocalImpl(private val songDAO: SongDAO) : TrackDataSource.Local {

    override suspend fun insertTrack(track: Track) {
        songDAO.insert(track)
    }

    override suspend fun deleteTrack(track: Track) {
        songDAO.delete(track)
    }

    override suspend fun getAllTrack(): List<Track> {
        return songDAO.getAllTrack()
    }

    override suspend fun isFavorite(idTrack: String): Boolean {
        return songDAO.isFavorite(idTrack)
    }
}
