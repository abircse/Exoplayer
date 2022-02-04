package com.coxtunes.exoplayer

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.coxtunes.exoplayer.databinding.ActivityVideoplayerlocalMainBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.util.Util


private const val TAG = "VideoPlayerLocalMainAct"

class VideoPlayerLocalMainActivity : AppCompatActivity() {

    private val videoPathUri: Uri = Uri.parse("file:///android_asset/localvideo.mp4")
    private var player: ExoPlayer? = null
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition = 0L

    private val viewBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityVideoplayerlocalMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
    }

    /** Initialize Player **/
    private fun initializePlayer() {
        player = ExoPlayer.Builder(this)
            .build()
            .also { exoPlayer ->
                viewBinding.playerView.player = exoPlayer
                exoPlayer.playWhenReady = true
                viewBinding.playerView.player = exoPlayer
                val firstItem = MediaItem.fromUri(videoPathUri)
                exoPlayer.addMediaItem(firstItem)
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
}