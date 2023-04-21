package com.xekombik.weatherapp.presentation

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
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
                val weather = vm.loadWeather("55.85, 37")
                runOnUiThread {
                    Log.d(
                        "WEATHER_TEST",
                        (weather.location.name) + "  " +
                                (weather.current.last_updated) + " " +
                                (weather.current.temp_c) + " " +
                                (weather.location.localtime)
                    )
                }
            }
        }
        val thb = findViewById<Button>(R.id.thb)
        val ndb = findViewById<Button>(R.id.ndb)

        thb.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val weatherToday = vm.getWeatherTodayHistory("55.85, 37", "2023-01-01")
                runOnUiThread {
                    for (item in weatherToday){
                        Log.d("WEATHER_TEST", item.time + " " + item.temp_c + " " + item.condition.text)
                    }
                }
            }
        }

        ndb.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val weatherFuture = vm.getFutureWeatherHistory("55.85, 37", "2023-05-20")
                runOnUiThread {
                    for (item in weatherFuture){
                        Log.d("WEATHER_TEST", item.time + " " + item.temp_c)
                    }
                }
            }
        }


    }
}