package com.silverorange.videoplayer.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.silverorange.videoplayer.network.Api
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ViewModel: ViewModel() {

    private val _status = MutableLiveData<String>()

    val status: LiveData<String> = _status

    private fun getAllVideos() {
        Log.d("VideoPlayerCheck", "getAllVideos")
        viewModelScope.launch {
            Log.d("VideoPlayerCheck", "viewModelScope")
            try {
                Log.d("VideoPlayerCheck", "listResult.size")
                val listResult = Api.retrofitService.getVideos()
                Log.d("VideoPlayerCheck", "listResult")
                _status.value = "Success: ${listResult.size} videos retrieved"
            } catch (e: Exception) {
                e.message?.let { Log.d("VideoPlayerCheck", it) }
                _status.value = "Failure: ${e.message}"
            }
        }
    }

    init {
        Log.d("VideoPlayerCheck", "Init")
        getAllVideos()
    }
}