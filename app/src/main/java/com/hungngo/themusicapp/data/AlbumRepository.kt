package com.hungngo.themusicapp.data

import com.hungngo.themusicapp.data.model.AlbumResponse
import com.hungngo.themusicapp.utils.DataResult

interface AlbumRepository {

    suspend fun getAlbumByID(id: String): DataResult<AlbumResponse>
}
