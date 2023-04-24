package com.xekombik.weatherapp.presentation

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.xekombik.weatherapp.R

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val gitHubButton = findViewById<ImageButton>(R.id.githubLink)
        gitHubButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Xek0mb1k/WeatherApp"))
            startActivity(intent)
        }
    }

}
