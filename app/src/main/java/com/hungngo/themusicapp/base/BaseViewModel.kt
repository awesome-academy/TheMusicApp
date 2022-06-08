package com.hungngo.themusicapp.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hungngo.themusicapp.utils.DataResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {
    var isLoading = MutableLiveData<Boolean>()
    private var loadingCount = 0
    var errorResponse = MutableLiveData<String>()

    protected fun <T> launchTaskSync(
        onRequest: suspend CoroutineScope.() -> DataResult<T>,
        onSuccess: (T) -> Unit = {},
        onError: (String) -> Unit = {},
        isShowLoading: Boolean = true
    ) = viewModelScope.launch {

        showLoading(isShowLoading)
        when (val asynchronousTasks = onRequest(this)) {
            is DataResult.Success -> asynchronousTasks.data?.let { onSuccess(it) }
            is DataResult.Error -> {
                asynchronousTasks.message?.let {
                    errorResponse.postValue(it)
                    onError(it) }
            }
        }
        hideLoading(isShowLoading)
    }

    private fun showLoading(isShowLoading: Boolean) {
        if (!isShowLoading) return
        loadingCount++
        if (isLoading.value != true) isLoading.value = true
    }

    private fun hideLoading(isShowLoading: Boolean) {
        if (!isShowLoading) return
        loadingCount--
        if (loadingCount <= 0) {
            loadingCount = 0
            isLoading.value = false
        }
    }
}
