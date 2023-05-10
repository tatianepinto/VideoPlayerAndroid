package com.silverorange.videoplayer.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.silverorange.videoplayer.network.Api
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ViewModel: ViewModel() {

    private val _status = MutableLiveData<String>()

    val status: LiveData<String> = _status

    init {
        getAllVideos()
    }

    private fun getAllVideos() {
        viewModelScope.launch {
            try {
                val listResult = Api.retrofitService.getVideos()
                _status.value = "Success: ${listResult.size} videos retrieved"
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }
}