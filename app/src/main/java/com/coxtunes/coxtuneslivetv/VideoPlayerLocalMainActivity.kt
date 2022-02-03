package com.coxtunes.coxtuneslivetv

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.coxtunes.coxtuneslivetv.databinding.ActivityVideoplayerlocalMainBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem


class VideoPlayerLocalMainActivity : AppCompatActivity() {

    lateinit var binding: ActivityVideoplayerlocalMainBinding
    private var exoPlayer: ExoPlayer? = null
    private var playbackPosition = 0L
    private var playWhenReady = true
    private val videoPathUri: Uri = Uri.parse("file:///android_asset/localvideo.mp4")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoplayerlocalMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preparePlayer()
    }

    private fun preparePlayer() {
        exoPlayer = ExoPlayer.Builder(this).build()
        exoPlayer?.playWhenReady = true
        binding.playerView.player = exoPlayer
        val firstItem = MediaItem.fromUri(videoPathUri)
        exoPlayer?.addMediaItem(firstItem)
        exoPlayer?.seekTo(playbackPosition)
        exoPlayer?.playWhenReady = playWhenReady
        exoPlayer?.prepare()
    }

    private fun releasePlayer() {
        exoPlayer?.let { player ->
            playbackPosition = player.currentPosition
            playWhenReady = player.playWhenReady
            player.release()
            exoPlayer = null
        }
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    override fun onPause() {
        super.onPause()
        releasePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }
}