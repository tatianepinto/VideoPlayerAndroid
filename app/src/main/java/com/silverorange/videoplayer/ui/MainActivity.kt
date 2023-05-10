package com.silverorange.videoplayer.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.silverorange.videoplayer.R
import com.silverorange.videoplayer.overview.ViewModel
import kotlinx.android.synthetic.main.activity_main.et_name

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: ViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(ViewModel::class.java)

        et_name.text = viewModel.status.toString()
    }
}