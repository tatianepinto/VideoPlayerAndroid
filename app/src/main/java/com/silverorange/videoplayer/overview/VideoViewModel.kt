package com.silverorange.videoplayer.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.silverorange.videoplayer.network.Api
import androidx.lifecycle.viewModelScope
import com.silverorange.videoplayer.network.Author
import com.silverorange.videoplayer.network.Video
import kotlinx.coroutines.launch

class VideoViewModel: ViewModel() {

    private val _status = MutableLiveData<String>()
    private val _allVideos = MutableLiveData<List<Video>>()

    val status: LiveData<String> = _status
    val allVideos: LiveData<List<Video>> = _allVideos

    private fun getAllVideos() {
        viewModelScope.launch {
            try {
                val listResult = Api.retrofitService.getVideos().sortedBy { it.publishedAt }
                _status.value = "Success: ${listResult.size} videos retrieved"
                _allVideos.value = listResult
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