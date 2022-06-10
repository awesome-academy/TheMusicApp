package com.hungngo.themusicapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hungngo.themusicapp.base.BaseViewModel
import com.hungngo.themusicapp.data.PlayListRepository
import com.hungngo.themusicapp.data.model.PlayListResponse

class HomeViewModel(private val playListRepository: PlayListRepository) : BaseViewModel() {

    private var _playlists: MutableLiveData<PlayListResponse> = MutableLiveData()
    val playlists: LiveData<PlayListResponse> get() = _playlists

    fun getPlayList(playListID: String) {
        launchTaskSync(
            onRequest = { playListRepository.getPlayListByID(playListID) },
            onSuccess = { _playlists.postValue(it)}
        )
    }
}
