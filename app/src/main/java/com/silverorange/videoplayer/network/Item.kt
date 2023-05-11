package com.silverorange.videoplayer.network

import com.squareup.moshi.Json

data class Item(
    val id: String,
    @Json(name = "fullURL") val fullURL: String
)
