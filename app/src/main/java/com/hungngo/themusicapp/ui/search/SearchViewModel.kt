package com.hungngo.themusicapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hungngo.themusicapp.base.BaseViewModel
import com.hungngo.themusicapp.data.TrackRepository
import com.hungngo.themusicapp.data.model.SearchTrackResponse

class SearchViewModel(private val trackRepository: TrackRepository) : BaseViewModel() {

    private var _tracks: MutableLiveData<SearchTrackResponse> = MutableLiveData()
    val tracks: LiveData<SearchTrackResponse> get() = _tracks

    fun searchTrack(query: String, type: String) {
        launchTaskSync(
            onRequest = { trackRepository.searchItem(query, type) },
            onSuccess = { _tracks.postValue(it) }
        )
    }
}
