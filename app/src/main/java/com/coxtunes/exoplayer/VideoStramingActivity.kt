package com.coxtunes.exoplayer

import android.content.DialogInterface
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.coxtunes.exoplayer.trackselectionDialog.TrackSelectionDialog
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackParameters
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView

class VideoStramingActivity : AppCompatActivity() {

    private var isShowingTrackSelectionDialog = false
    private var trackSelector: DefaultTrackSelector? = null
    var speed = arrayOf("0.25x", "0.5x", "Normal", "1.5x", "2x")

    //demo url
    var url1 = "https://mprod-cdn.toffeelive.com/toffee/fifa-free/match-1/master_1000.m3u8"

    //208A8A8A
    private lateinit var playerview: PlayerView
    private var simpleExoPlayer: ExoPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_streaming)
        trackSelector = DefaultTrackSelector(this)
        simpleExoPlayer = ExoPlayer.Builder(this).setTrackSelector(trackSelector!!).build()
        playerview = findViewById(R.id.video_view)
        playerview.player = simpleExoPlayer
        val mediaItem = MediaItem.fromUri(url1)
        simpleExoPlayer!!.addMediaItem(mediaItem)
        simpleExoPlayer!!.prepare()
        simpleExoPlayer!!.play()
        val farwordBtn = playerview.findViewById<ImageView>(R.id.fwd)
        val rewBtn = playerview.findViewById<ImageView>(R.id.rew)
        val setting = playerview.findViewById<ImageView>(R.id.exo_track_selection_view)
        val speedBtn = playerview.findViewById<ImageView>(R.id.exo_playback_speed)
        val speedTxt = playerview.findViewById<TextView>(R.id.speed)
        speedBtn.setOnClickListener {
            val builder = AlertDialog.Builder(this@VideoStramingActivity)
            builder.setTitle("Set Speed")
            builder.setItems(speed) { dialog, which ->
                // the user clicked on colors[which]
                if (which == 0) {
                    //  speedTxt.setVisibility(View.VISIBLE);
                    //   speedTxt.setText("0.25X");
                    val param = PlaybackParameters(0.5f)
                    simpleExoPlayer!!.playbackParameters = param
                }
                if (which == 1) {
                    // speedTxt.setVisibility(View.VISIBLE);
                    // speedTxt.setText("0.5X");
                    val param = PlaybackParameters(0.5f)
                    simpleExoPlayer!!.playbackParameters = param
                }
                if (which == 2) {
                    // speedTxt.setVisibility(View.GONE);
                    val param = PlaybackParameters(1f)
                    simpleExoPlayer!!.playbackParameters = param
                }
                if (which == 3) {
                    //  speedTxt.setVisibility(View.VISIBLE);
                    //  speedTxt.setText("1.5X");
                    val param = PlaybackParameters(1.5f)
                    simpleExoPlayer!!.playbackParameters = param
                }
                if (which == 4) {
                    // speedTxt.setVisibility(View.VISIBLE);
                    //  speedTxt.setText("2X");
                    val param = PlaybackParameters(2f)
                    simpleExoPlayer!!.playbackParameters = param
                }
            }
            builder.show()
        }
        farwordBtn.setOnClickListener { simpleExoPlayer!!.seekTo(simpleExoPlayer!!.currentPosition + 10000) }
        rewBtn.setOnClickListener {
            val num = simpleExoPlayer!!.currentPosition - 10000
            if (num < 0) {
                simpleExoPlayer!!.seekTo(0)
            } else {
                simpleExoPlayer!!.seekTo(simpleExoPlayer!!.currentPosition - 10000)
            }
        }
        val fullscreenButton = playerview.findViewById<ImageView>(R.id.fullscreen)
        fullscreenButton.setOnClickListener {
            val orientation = this@VideoStramingActivity.resources.configuration.orientation
            requestedOrientation = if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                // code for portrait mode
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            } else {
                // code for landscape mode

                //   Toast.makeText(MainActivity.this, "Land", Toast.LENGTH_SHORT).show();
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
        }
//        findViewById<View>(R.id.video_view).setOnClickListener { simpleExoPlayer!!.play() }
//        findViewById<View>(R.id.video_view).setOnClickListener { simpleExoPlayer!!.pause() }
        simpleExoPlayer!!.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == ExoPlayer.STATE_ENDED) {
                }
            }
        })

        /*
        playerview.setControllerVisibilityListener{


        }

         */

        setting.setOnClickListener {
            if (!isShowingTrackSelectionDialog
                && TrackSelectionDialog.willHaveContent(trackSelector!!)
            ) {
                isShowingTrackSelectionDialog = true
                val trackSelectionDialog = TrackSelectionDialog.createForTrackSelector(
                    trackSelector!!
                )  /* onDismissListener= */
                { dismissedDialog: DialogInterface? -> isShowingTrackSelectionDialog = false }
                trackSelectionDialog.show(supportFragmentManager,  /* tag= */null)
            }
        }
    }

    protected fun releasePlayer() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer!!.release()
            simpleExoPlayer = null
            trackSelector = null
        }
    }

    public override fun onStop() {
        super.onStop()
        releasePlayer()
    }
}