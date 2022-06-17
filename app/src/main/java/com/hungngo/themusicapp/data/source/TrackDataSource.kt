package com.hungngo.themusicapp.data.source

import com.hungngo.themusicapp.data.model.TrackRespond

interface TrackDataSource {

    interface Remote {
        suspend fun getTrackByID(idTrack: String): TrackRespond
    }
}
