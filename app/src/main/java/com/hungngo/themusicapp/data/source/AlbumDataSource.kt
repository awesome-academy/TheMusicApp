package com.hungngo.themusicapp.data.source

import com.hungngo.themusicapp.data.model.AlbumResponse

interface AlbumDataSource {

    interface Remote {
        suspend fun getAlbumByID(id: String): AlbumResponse
    }
}
