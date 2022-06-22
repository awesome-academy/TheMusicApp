package com.hungngo.themusicapp.ui.playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hungngo.themusicapp.base.BaseViewModel
import com.hungngo.themusicapp.data.AlbumRepository
import com.hungngo.themusicapp.data.TrackRepository
import com.hungngo.themusicapp.data.model.AlbumResponse
import com.hungngo.themusicapp.data.model.Track
import kotlinx.coroutines.runBlocking

class PlaylistViewModel(
    private val albumRepository: AlbumRepository,
    private val trackRepository: TrackRepository
) : BaseViewModel() {

    private var _albums: MutableLiveData<AlbumResponse> = MutableLiveData()
    val album: LiveData<AlbumResponse> get() = _albums

    fun getAlbumByID(id: String) {
        launchTaskSync(
            onRequest = { albumRepository.getAlbumByID(id) },
            onSuccess = { _albums.postValue(it) }
        )
    }

    fun isFavorite(track: Track): Boolean {
        return runBlocking {
            trackRepository.isFavorite(track.id)
        }
    }
}
