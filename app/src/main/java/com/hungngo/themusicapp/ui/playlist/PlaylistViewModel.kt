package com.hungngo.themusicapp.ui.playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hungngo.themusicapp.base.BaseViewModel
import com.hungngo.themusicapp.data.AlbumRepository
import com.hungngo.themusicapp.data.model.AlbumResponse

class PlaylistViewModel(
    private val albumRepository: AlbumRepository
) : BaseViewModel() {

    private var _albums: MutableLiveData<AlbumResponse> = MutableLiveData()
    val album: LiveData<AlbumResponse> get() = _albums

    fun getAlbumByID(id: String) {
        launchTaskSync(
            onRequest = { albumRepository.getAlbumByID(id) },
            onSuccess = { _albums.postValue(it) }
        )
    }
}
