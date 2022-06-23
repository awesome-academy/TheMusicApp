package com.hungngo.themusicapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hungngo.themusicapp.base.BaseViewModel
import com.hungngo.themusicapp.data.AlbumRepository
import com.hungngo.themusicapp.data.LyricRepository
import com.hungngo.themusicapp.data.TrackRepository
import com.hungngo.themusicapp.data.model.AlbumResponse
import com.hungngo.themusicapp.data.model.LyricsResponse
import com.hungngo.themusicapp.data.model.Track
import com.hungngo.themusicapp.utils.Constant
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainViewModel(
    private val albumRepository: AlbumRepository,
    private val lyricRepository: LyricRepository,
    private val trackRepository: TrackRepository
) : BaseViewModel() {

    private val _albums: MutableLiveData<AlbumResponse> = MutableLiveData()
    val albums: LiveData<AlbumResponse> get() = _albums

    private val _lyrics: MutableLiveData<LyricsResponse> = MutableLiveData()
    val lyric: LiveData<LyricsResponse> get() = _lyrics

    private val _tracks: MutableLiveData<List<Track>> = MutableLiveData()
    val tracks: LiveData<List<Track>> get() = _tracks

    private val _toastMessage: MutableLiveData<String> = MutableLiveData()
    val toastMessage: LiveData<String> get() = _toastMessage

    fun getAlbumByID(idAlbum: String) {
        launchTaskSync(
            onRequest = { albumRepository.getAlbumByID(idAlbum) },
            onSuccess = {
                _albums.postValue(it)
            }
        )
    }

    fun getLyricsByID(id: String) {
        launchTaskSync(
            onRequest = { lyricRepository.getLyricsByID(id) },
            onSuccess = { _lyrics.postValue(it) }
        )
    }

    fun insertTrack(track: Track) {
        viewModelScope.launch {
            trackRepository.insertTrack(track)
            isFavorite(track.id)
        }
    }

    fun getAllTrack() {
        launchTaskSync(
            onRequest = { trackRepository.getAllTrack() },
            onSuccess = { _tracks.postValue(it) }
        )
    }

    fun deleteTrack(track: Track) {
        launchTaskSync(
            onRequest = { trackRepository.deleteTrack(track) },
            onSuccess = {
                getAllTrack()
                _toastMessage.postValue(Constant.MESSAGE_DELETE_SUCCESS)
            }
        )
    }

    fun isFavorite(idTrack: String): Boolean {
        return runBlocking {
            trackRepository.isFavorite(idTrack)
        }
    }
}
