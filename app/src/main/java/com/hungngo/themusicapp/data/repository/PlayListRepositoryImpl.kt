package com.hungngo.themusicapp.data.repository

import com.hungngo.themusicapp.base.BaseRepository
import com.hungngo.themusicapp.data.PlayListRepository
import com.hungngo.themusicapp.data.source.PlayListDataSource

class PlayListRepositoryImpl(private val remote: PlayListDataSource.Remote) : BaseRepository(),
    PlayListRepository {

    override suspend fun getPlayListByID(playListID: String) = withResultContext {
        remote.getPlayListByID(playListID)
    }
}
