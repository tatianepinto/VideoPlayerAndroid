package com.silverorange.videoplayer.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.silverorange.videoplayer.R
import com.silverorange.videoplayer.adapter.VideosRecyclerViewAdapter
import com.silverorange.videoplayer.overview.VideoViewModel
import kotlinx.android.synthetic.main.activity_main.et_name
import kotlinx.android.synthetic.main.activity_main.video_main
import kotlinx.android.synthetic.main.video_view_holder.et_title

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: VideoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()
    }

    private fun goToVideoDetail(videoItem: String?) {
        val intent = Intent(this, VideoDetailActivity::class.java)
        videoItem.let { intent.putExtra("id", it) }
        startActivity(intent)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[VideoViewModel::class.java]

        viewModel.status.observe(this, Observer {
            Log.d("VideoPlayerCheck", it)
            et_name.text = it
        } )

        viewModel.allVideos.observe(this, Observer {
            video_main.setVideoURI(Uri.parse(it[0].fullURL))
        } )
    }
}