package com.silverorange.videoplayer.ui

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
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
    private var currentVideoIndex: Int = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel(currentVideoIndex)
        setupBackButton()
    }

    private fun setupBackButton() {
        val backButton = findViewById<ImageButton>(R.id.ib_back)
        val nextButton = findViewById<ImageButton>(R.id.ib_next)
        backButton.isEnabled = false
        backButton.alpha = 0.5f
        nextButton.isEnabled = true
        nextButton.alpha = 1f
    }

    private fun setupNextButton() {
        val backButton = findViewById<ImageButton>(R.id.ib_back)
        val nextButton = findViewById<ImageButton>(R.id.ib_next)
        backButton.isEnabled = true
        backButton.alpha = 1f
        nextButton.isEnabled = false
        nextButton.alpha = 0.5f
    }

    private fun goToVideoDetail(videoItem: String?) {
        val intent = Intent(this, VideoDetailActivity::class.java)
        videoItem.let { intent.putExtra("id", it) }
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupViewModel(currentVideoIndex: Int) {
        viewModel = ViewModelProvider(this)[VideoViewModel::class.java]

        viewModel.status.observe(this, Observer {
            Log.d("VideoPlayerCheck", it)
            et_name.text = it
        } )

        setupScreenInfo(this.currentVideoIndex)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupScreenInfo(index: Int) {
        viewModel.allVideos.observe(this, Observer {
            Log.d("VideoPlayerCheck", "Index: "+index)
            if (index === 0) setupBackButton()
            if (index + 1 === it.size) setupNextButton()
            video_main.setVideoURI(Uri.parse(it[index].fullURL))
            video_main.start()
            et_title.text = it[index].title
            val instant = Instant.parse(it[index].publishedAt)
            val date = Date.from(instant)
            val publishedDate = SimpleDateFormat("MMMM dd, yyyy", Locale.US).format(date)
            et_published.text = "Published: $publishedDate"
            et_description.text = it[index].description
        } )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onNextClick(view: View) {
        currentVideoIndex += 1
        setupViewModel(currentVideoIndex)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun onBackClick(view: View) {
        currentVideoIndex -= 1
        setupViewModel(currentVideoIndex)
    }
}