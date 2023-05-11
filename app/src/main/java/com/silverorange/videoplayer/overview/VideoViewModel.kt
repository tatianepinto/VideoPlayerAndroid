package com.silverorange.videoplayer.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.silverorange.videoplayer.network.Api
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class VideoViewModel: ViewModel() {

    private val _status = MutableLiveData<String>()

    val status: LiveData<String> = _status

    private fun getAllVideos() {
        viewModelScope.launch {
            try {
                val listResult = Api.retrofitService.getVideos()
                _status.value = "Success: ${listResult.size} videos retrieved"
            } catch (e: Exception) {
                e.message?.let { Log.d("VideoPlayerCheck", it) }
                _status.value = "Failure: ${e.message}"
            }
        }
    }

    init {
        getAllVideos()
    }
}