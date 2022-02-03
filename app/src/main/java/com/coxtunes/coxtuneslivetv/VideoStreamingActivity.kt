package com.coxtunes.coxtuneslivetv

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.coxtunes.coxtuneslivetv.databinding.ActivityVideoStreamingBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource

class VideoStreamingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoStreamingBinding
    private var exoPlayer: ExoPlayer? = null
    private var playbackPosition = 0L
    private var playWhenReady = true
    private val url = "https://tempx.jagobd.com:441/c5V6mmMyX7RpbEU9Mi8xNy8yMDEOGIDU6RgzQ6NTAgdEoaeFzbF92YWxIZTO0U0ezN1IzMyfvcGVMZEJCTEFWeVN3PT0mdFsaWRtaW51aiPhnPTI/independent-8-org.stream/chunks.m3u8"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoStreamingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preparePlayer()
    }

    private fun preparePlayer() {
        exoPlayer = ExoPlayer.Builder(this).build()
        exoPlayer?.playWhenReady = true
        binding.playerViewUrl.player = exoPlayer
        val defaultHttpDataSourceFactory = DefaultHttpDataSource.Factory()
        val mediaItem = MediaItem.fromUri(url)
        val mediaSource = HlsMediaSource.Factory(defaultHttpDataSourceFactory).createMediaSource(mediaItem)
        exoPlayer?.setMediaSource(mediaSource)
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

    override fun onResume() {
        super.onResume()
        preparePlayer()
    }
}