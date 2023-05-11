package com.silverorange.videoplayer.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.media.ThumbnailUtils
import android.os.Build
import android.provider.MediaStore.Images.Thumbnails.MINI_KIND
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.silverorange.videoplayer.R
import com.silverorange.videoplayer.network.Video
import com.silverorange.videoplayer.util.ImageConverter
import java.net.URL
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale


class VideosRecyclerViewAdapter (val clickListener: (videoItem: String)-> Unit) :
    RecyclerView.Adapter<VideosRecyclerViewAdapter.VideoViewHolder>() {

    private var videosData = emptyList<Video>()

    class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val listImg: ImageView = itemView.findViewById(R.id.im_video)
        val listTitle: TextView = itemView.findViewById(R.id.et_title)
        val listPublished: TextView = itemView.findViewById(R.id.et_published)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.video_view_holder, parent, false)
        return VideoViewHolder(view)
    }

    override fun getItemCount() = videosData.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val thumbMini = ImageConverter.getVideoThumbnail(videosData[position].fullURL);
        if (thumbMini != null) holder.listImg.setImageBitmap(thumbMini)

        holder.listTitle.text = videosData[position].title

        val instant = Instant.parse(videosData[position].publishedAt)
        val date = Date.from(instant)
        val publishedDate = SimpleDateFormat("MM/dd/yyyy", Locale.US).format(date)
        holder.listPublished.text = "Published: $publishedDate"

        holder.itemView.setOnClickListener {
            clickListener(videosData[position].id.toString())
        }
    }

    internal fun setVideos(videos: List<Video>) {
        this.videosData = videos
        notifyDataSetChanged()
    }
}