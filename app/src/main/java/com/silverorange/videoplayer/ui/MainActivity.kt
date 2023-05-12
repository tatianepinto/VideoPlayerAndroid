package com.silverorange.videoplayer.ui

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.silverorange.videoplayer.R
import com.silverorange.videoplayer.overview.VideoViewModel
import kotlinx.android.synthetic.main.activity_main.et_description
import kotlinx.android.synthetic.main.activity_main.et_name
import kotlinx.android.synthetic.main.activity_main.et_published
import kotlinx.android.synthetic.main.activity_main.et_title
import kotlinx.android.synthetic.main.activity_main.video_main
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: VideoViewModel
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()
        setupButtons()
    }

    private fun setupButtons() {
        val backButton = findViewById<ImageButton>(R.id.ib_back)
        backButton.isEnabled = false
        backButton.alpha = 0.5f
    }

    private fun goToVideoDetail(videoItem: String?) {
        val intent = Intent(this, VideoDetailActivity::class.java)
        videoItem.let { intent.putExtra("id", it) }
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[VideoViewModel::class.java]

        viewModel.status.observe(this, Observer {
            Log.d("VideoPlayerCheck", it)
            et_name.text = it
        } )

        viewModel.allVideos.observe(this, Observer {
            video_main.setVideoURI(Uri.parse(it[0].fullURL))
            video_main.start()
            et_title.text = it[0].title
            val instant = Instant.parse(it[0].publishedAt)
            val date = Date.from(instant)
            val publishedDate = SimpleDateFormat("MMMM dd, yyyy", Locale.US).format(date)
            et_published.text = "Published: $publishedDate"
            et_description.text = it[0].description
        } )
    }
}