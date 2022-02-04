package com.coxtunes.exoplayer


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.coxtunes.exoplayer.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonAudio.setOnClickListener {
            goToActivity(AudioStreamingActivity::class.java)
        }

        binding.buttonVideo.setOnClickListener {
            goToActivity(VideoStreamingActivity::class.java)
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
