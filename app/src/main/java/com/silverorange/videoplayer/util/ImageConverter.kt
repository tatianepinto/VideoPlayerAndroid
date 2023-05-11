package com.silverorange.videoplayer.util

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import java.net.URL

class ImageConverter {
    companion object {
        fun getVideoThumbnail(videoUrl: String): Bitmap? {
            val retriever = MediaMetadataRetriever()
            try {
                retriever.setDataSource(URL(videoUrl).toString(), HashMap<String, String>())
                return retriever.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST_SYNC)
            } catch (ex: Exception) {
                ex.printStackTrace()
            } finally {
                retriever.release()
            }
            return null
        }
    }
}