package com.hungngo.themusicapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hungngo.themusicapp.base.BaseViewModel
import com.hungngo.themusicapp.data.AlbumRepository
import com.hungngo.themusicapp.data.PlayListRepository
import com.hungngo.themusicapp.data.model.Album
import com.hungngo.themusicapp.data.model.AlbumResponse
import com.hungngo.themusicapp.data.model.LyricsResponse
import com.hungngo.themusicapp.data.model.PlayListResponse
import com.hungngo.themusicapp.utils.Constant

class HomeViewModel(
    private val albumRepository: AlbumRepository,
    private val playListRepository: PlayListRepository
) : BaseViewModel() {

    private var _playlists: MutableLiveData<PlayListResponse> = MutableLiveData()
    val playlists: LiveData<PlayListResponse> get() = _playlists

    private var _albums: MutableLiveData<AlbumResponse> = MutableLiveData()
    val albums: LiveData<AlbumResponse> get() = _albums

    init {
        getAlbumByID(Constant.ALBUM_ID_RANDOM_1)
    }

    fun getPlayList(playListID: String) {
        launchTaskSync(
            onRequest = { playListRepository.getPlayListByID(playListID) },
            onSuccess = { _playlists.postValue(it)}
        )
    }

    fun getAlbumByID(idAlbum: String) {
        launchTaskSync(
            onRequest = { albumRepository.getAlbumByID(idAlbum) },
            onSuccess = { _albums.postValue(it) }
        )
    }
}
