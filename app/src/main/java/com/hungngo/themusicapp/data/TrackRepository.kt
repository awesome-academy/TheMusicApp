package com.hungngo.themusicapp.data

import com.hungngo.themusicapp.data.model.SearchTrackResponse
import com.hungngo.themusicapp.utils.DataResult

interface TrackRepository {

    suspend fun searchItem(query: String, type: String): DataResult<SearchTrackResponse>
}
