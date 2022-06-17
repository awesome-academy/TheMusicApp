package com.hungngo.themusicapp.data

import com.hungngo.themusicapp.data.model.TrackRespond
import com.hungngo.themusicapp.utils.DataResult

interface TrackRepository {

    suspend fun getTrackByID(idTrack: String) : DataResult<TrackRespond>
}
