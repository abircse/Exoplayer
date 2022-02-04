package com.coxtunes.exoplayer

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.coxtunes.exoplayer.databinding.ActivityAudiostramingBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.source.MediaSourceFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource

private const val TAG = "AudioStreamingActivity"
class AudioStreamingActivity : AppCompatActivity() {

    private lateinit var player: ExoPlayer
    private val radioUrl = "http://kastos.cdnstream.com/1345_32"

    private val viewBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityAudiostramingBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        initListeners()
        prepareMediaPlayer()
    }

    /** Initialize Media Audio Player **/
    private fun prepareMediaPlayer() {

        val mediaDataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
        val mediaSource = ProgressiveMediaSource.Factory(mediaDataSourceFactory).createMediaSource(MediaItem.fromUri(radioUrl))
        val mediaSourceFactory: MediaSourceFactory = DefaultMediaSourceFactory(mediaDataSourceFactory)

        player = ExoPlayer.Builder(this)
            .setMediaSourceFactory(mediaSourceFactory)
            .build()
            .also {
                it.addMediaSource(mediaSource)
                it.prepare()
            }

        /** Handle error if detect **/
        player.addListener(object : Player.Listener {
            override fun onPlayerError(error: PlaybackException) {
                Log.d(TAG, "onPlayerError: ${error.message}")
            }
        })
    }

    private fun initListeners() {
        viewBinding.btnStart.setOnClickListener {
            player.playWhenReady = true
        }

        viewBinding.btnStop.setOnClickListener {
            player.playWhenReady = false
        }
    }

    override fun onDestroy() {
        player.playWhenReady = false
        super.onDestroy()
    }
}