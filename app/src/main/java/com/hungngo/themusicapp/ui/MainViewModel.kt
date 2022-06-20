package com.hungngo.themusicapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hungngo.themusicapp.base.BaseViewModel
import com.hungngo.themusicapp.data.AlbumRepository
import com.hungngo.themusicapp.data.TrackRepository
import com.hungngo.themusicapp.data.model.AlbumResponse
import com.hungngo.themusicapp.data.model.TrackRespond

class MainViewModel(
    private val albumRepository: AlbumRepository,
    private val trackRepository: TrackRepository
) : BaseViewModel() {

    private var _albums: MutableLiveData<AlbumResponse> = MutableLiveData()
    val albums: LiveData<AlbumResponse> get() = _albums

    private var _tracks: MutableLiveData<TrackRespond> = MutableLiveData()
    val tracks: LiveData<TrackRespond> get() = _tracks

    fun getAlbumByID(idAlbum: String) {
        launchTaskSync(
            onRequest = { albumRepository.getAlbumByID(idAlbum) },
            onSuccess = { _albums.postValue(it) }
        )
    }

    fun getTrackByID(idTrack: String) {
        launchTaskSync(
            onRequest = { trackRepository.getTrackByID(idTrack) },
            onSuccess = { _tracks.postValue(it) }
        )
    }
}
