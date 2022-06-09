package com.hungngo.themusicapp.data

import com.hungngo.themusicapp.data.model.PlayListResponse
import com.hungngo.themusicapp.utils.DataResult

interface PlayListRepository {

    suspend fun getPlayListByID(playListID: String): DataResult<PlayListResponse>
}
