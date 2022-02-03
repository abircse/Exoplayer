package com.coxtunes.coxtuneslivetv

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.coxtunes.coxtuneslivetv.databinding.ActivityAudiostramingBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.source.MediaSourceFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.Util

class AudioStreamingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAudiostramingBinding
    private lateinit var exoPlayer: ExoPlayer
    private val radioUrl = "http://kastos.cdnstream.com/1345_32"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAudiostramingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prepareMediaPlayer()
        initListeners()
    }

    private fun prepareMediaPlayer() {
        val mediaDataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
        val mediaSource = ProgressiveMediaSource.Factory(mediaDataSourceFactory)
            .createMediaSource(MediaItem.fromUri(radioUrl))
        val mediaSourceFactory: MediaSourceFactory =
            DefaultMediaSourceFactory(mediaDataSourceFactory)
        exoPlayer = ExoPlayer.Builder(this).setMediaSourceFactory(mediaSourceFactory).build()
        exoPlayer.addMediaSource(mediaSource)
        exoPlayer.prepare()
    }

    private fun initListeners() {
        binding.btnStart.setOnClickListener {
            exoPlayer.playWhenReady = true
        }

        binding.btnStop.setOnClickListener {
            exoPlayer.playWhenReady = false
        }
    }

    override fun onDestroy() {
        exoPlayer.playWhenReady = false
        super.onDestroy()
    }
}