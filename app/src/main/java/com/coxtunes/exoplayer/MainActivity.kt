package com.coxtunes.exoplayer


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.coxtunes.exoplayer.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    //https://mprod-cdn.toffeelive.com/toffee/fifa-free/match-1/master_1000.m3u8

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonAudio.setOnClickListener {
            goToActivity(AudioStreamingActivity::class.java)
        }

        binding.buttonVideo.setOnClickListener {
            goToActivity(VideoStramingActivity::class.java)
        }

        binding.buttonVideoLocal.setOnClickListener {
            goToActivity(VideoPlayerLocalMainActivity::class.java)
        }
    }

    private fun <T> goToActivity(destination: Class<T>) {
        val intent = Intent(this, destination)
        startActivity(intent)
    }
}
