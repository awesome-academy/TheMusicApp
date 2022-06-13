package com.hungngo.themusicapp.data.repository

import com.hungngo.themusicapp.base.BaseRepository
import com.hungngo.themusicapp.data.ItemSearchRepository
import com.hungngo.themusicapp.data.source.ItemSearchDataSource

class ItemSearchRepositoryImpl(
    val remote: ItemSearchDataSource.Remote
) : BaseRepository(), ItemSearchRepository {

    override suspend fun searchArtist(query: String, type: String) = withResultContext {
        remote.searchArtist(query, type)
    }
}
