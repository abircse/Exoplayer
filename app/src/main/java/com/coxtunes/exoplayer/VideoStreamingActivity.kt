package com.coxtunes.exoplayer

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.coxtunes.exoplayer.databinding.ActivityVideoStreamingBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.Util


private const val TAG = "VideoStreamingActivity"

class VideoStreamingActivity : AppCompatActivity() {

    private val url = "http://43.230.123.21:8080/live/zee-bangla.m3u8"

    private val viewBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityVideoStreamingBinding.inflate(layoutInflater)
    }

    private var player: ExoPlayer? = null
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
    }

    public override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
            initializePlayer()
        }
    }

    public override fun onResume() {
        super.onResume()
        if (Util.SDK_INT <= 23 || player == null) {
            initializePlayer()
        }
    }

    public override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            releasePlayer()
        }
    }

    public override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            releasePlayer()
        }
    }

    /** Initialize Player **/
    private fun initializePlayer() {
        player = ExoPlayer.Builder(this)
            .build()
            .also { exoPlayer ->
                viewBinding.videoView.player = exoPlayer
                exoPlayer.playWhenReady = true
                viewBinding.videoView.player = exoPlayer
                val defaultHttpDataSourceFactory = DefaultHttpDataSource.Factory()
                val mediaItem = MediaItem.fromUri(url)
                val mediaSource = HlsMediaSource.Factory(defaultHttpDataSourceFactory)
                    .createMediaSource(mediaItem)
                exoPlayer.setMediaSource(mediaSource)
                exoPlayer.seekTo(playbackPosition)
                exoPlayer.playWhenReady = playWhenReady
                exoPlayer.prepare()
            }

        /** Handle error if detect **/
        player?.addListener(object : Player.Listener {
            override fun onPlayerError(error: PlaybackException) {
                Log.d(TAG, "onPlayerError: ${error.message}")
            }
        })
    }

    /** Release/Stop player activities **/
    private fun releasePlayer() {
        player?.run {
            playbackPosition = this.currentPosition
            currentWindow = this.currentPeriodIndex
            playWhenReady = this.playWhenReady
            release()
        }
        player = null
    }
}


