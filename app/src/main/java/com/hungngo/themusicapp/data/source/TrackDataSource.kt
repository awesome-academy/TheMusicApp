package com.hungngo.themusicapp.data.source

import com.hungngo.themusicapp.data.model.SearchTrackResponse

interface TrackDataSource {

    interface Remote {
        suspend fun searchTrack(query: String, type: String) : SearchTrackResponse
    }
}
