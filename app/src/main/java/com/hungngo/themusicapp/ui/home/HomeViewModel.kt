package com.hungngo.themusicapp.ui.home

import androidx.lifecycle.MutableLiveData
import com.hungngo.themusicapp.base.BaseViewModel
import com.hungngo.themusicapp.data.PlayListRepository
import com.hungngo.themusicapp.data.model.PlayListResponse

class HomeViewModel(private val playListRepository: PlayListRepository) : BaseViewModel() {

    var playlists: MutableLiveData<PlayListResponse> = MutableLiveData()

    fun getPlayList(playListID: String) {
        launchTaskSync(
            onRequest = { playListRepository.getPlayListByID(playListID) },
            onSuccess = { playlists.postValue(it)}
        )
    }
}
