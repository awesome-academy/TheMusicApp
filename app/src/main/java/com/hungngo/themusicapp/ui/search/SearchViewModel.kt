package com.hungngo.themusicapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hungngo.themusicapp.base.BaseViewModel
import com.hungngo.themusicapp.data.ItemSearchRepository
import com.hungngo.themusicapp.data.model.ItemsSearchResponse

class SearchViewModel(
    private val artistRepository: ItemSearchRepository
) : BaseViewModel() {

    private var _searchResponse: MutableLiveData<ItemsSearchResponse> = MutableLiveData()
    val searchResponse: LiveData<ItemsSearchResponse> get() = _searchResponse

    fun searchItems(query: String, type: String) {
        launchTaskSync(
            onRequest = { artistRepository.searchArtist(query, type) },
            onSuccess = { _searchResponse.postValue(it) }
        )
    }
}
