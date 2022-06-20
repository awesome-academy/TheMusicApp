package com.hungngo.themusicapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hungngo.themusicapp.base.BaseViewModel
import com.hungngo.themusicapp.data.AlbumRepository
import com.hungngo.themusicapp.data.LyricRepository
import com.hungngo.themusicapp.data.model.AlbumResponse
import com.hungngo.themusicapp.data.model.LyricsResponse

class MainViewModel(
    private val albumRepository: AlbumRepository,
    private val lyricRepository: LyricRepository
) : BaseViewModel() {

    private var _albums: MutableLiveData<AlbumResponse> = MutableLiveData()
    val albums: LiveData<AlbumResponse> get() = _albums

    private var _lyrics: MutableLiveData<LyricsResponse> = MutableLiveData()
    val lyric: LiveData<LyricsResponse> get() = _lyrics

    fun getAlbumByID(idAlbum: String) {
        launchTaskSync(
            onRequest = { albumRepository.getAlbumByID(idAlbum) },
            onSuccess = { _albums.postValue(it) }
        )
    }

    fun getLyricsByID(id: String) {
        launchTaskSync(
            onRequest = { lyricRepository.getLyricsByID(id) },
            onSuccess = { _lyrics.postValue(it) }
        )
    }
}
