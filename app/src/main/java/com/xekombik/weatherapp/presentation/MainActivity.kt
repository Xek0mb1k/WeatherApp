package com.xekombik.weatherapp.presentation

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.xekombik.weatherapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val vm by viewModel<MainViewModel>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bu = findViewById<Button>(R.id.bu)

        bu.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                vm.loadWeather("55.852195, 37.567799")
                runOnUiThread {
//                    val weather = vm.currentWeather
//                    Log.d(
//                        "WEATHER_TEST",
//                        (weather.location.country) + "  " + (weather.current.last_updated)
//                    )
                }
            }



        }
    }
}