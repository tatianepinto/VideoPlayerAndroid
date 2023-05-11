package com.silverorange.videoplayer.network

import android.net.Uri
import com.squareup.moshi.Json

data class Video(
    val id: String,
    val title: String,
    @Json(name = "hlsURL") val hlsURL: String,
    @Json(name = "fullURL") val fullURL: String,
    val description: String,
    val publishedAt: String,
    val author: Author
)
