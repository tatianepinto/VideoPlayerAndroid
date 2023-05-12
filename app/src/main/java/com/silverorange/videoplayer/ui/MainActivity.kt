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
        disablePlayButton()
    }

    private fun disablePlayButton() {
        val playButton = findViewById<ImageButton>(R.id.ib_play)
        playButton.isEnabled = false
        playButton.alpha = 0.5f
        val pauseButton = findViewById<ImageButton>(R.id.ib_pause)
        pauseButton.isEnabled = true
        pauseButton.alpha = 1f
    }

    private fun disablePauseButton() {
        val pauseButton = findViewById<ImageButton>(R.id.ib_pause)
        pauseButton.isEnabled = false
        pauseButton.alpha = 0.5f
        val playButton = findViewById<ImageButton>(R.id.ib_play)
        playButton.isEnabled = true
        playButton.alpha = 1f
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
            if (index === 0) setupBackButton()
            if (index + 1 === it.size) setupNextButton()
            val url = if (it[index].fullURL.contains("/full/720.mp4")) it[index].fullURL
                else it[index].fullURL.replace("/720.mp4", "/full/720.mp4")
            video_main.setVideoURI(Uri.parse(url))
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

    fun onClickPlayVideo(view: View) {
        disablePlayButton()
        video_main.start()
    }

    fun onClickPauseVideo(view: View) {
        disablePauseButton()
        video_main.pause()
    }
}