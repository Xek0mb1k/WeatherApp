package com.xekombik.weatherapp.presentation


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.xekombik.weatherapp.databinding.ActivityWeatherDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class WeatherDetailActivity : AppCompatActivity() {

    private val vm by viewModel<MainViewModel>()
    private lateinit var binding: ActivityWeatherDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSupportActionBar(binding.weatherDetailToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)


        binding.toHomeButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }


}