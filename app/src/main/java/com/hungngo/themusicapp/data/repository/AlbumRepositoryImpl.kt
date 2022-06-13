package com.hungngo.themusicapp.data.repository

import com.hungngo.themusicapp.base.BaseRepository
import com.hungngo.themusicapp.data.AlbumRepository
import com.hungngo.themusicapp.data.model.AlbumResponse
import com.hungngo.themusicapp.data.source.AlbumDataSource
import com.hungngo.themusicapp.utils.DataResult

class AlbumRepositoryImpl(
    private val remote: AlbumDataSource.Remote
): BaseRepository(), AlbumRepository {

    override suspend fun getAlbumByID(id: String): DataResult<AlbumResponse> = withResultContext {
        remote.getAlbumByID(id)
    }
}
