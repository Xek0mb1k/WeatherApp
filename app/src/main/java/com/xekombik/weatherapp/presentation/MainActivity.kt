package com.xekombik.weatherapp.presentation

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.xekombik.weatherapp.R
import com.xekombik.weatherapp.databinding.ActivityChangeLocationBinding
import com.xekombik.weatherapp.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val vm by viewModel<MainViewModel>()
    private lateinit var binding: ActivityMainBinding


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.activityMainToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.locationTextView.setOnClickListener{
            val intent = Intent(this, ChangeLocationActivity::class.java)
            startActivity(intent)
        }

        binding.currentDayCardView.setOnClickListener{
            val intent = Intent(this, WeatherDetailActivity::class.java)
            startActivity(intent)
        }
//        val bu = findViewById<Button>(R.id.bu)
//        val thb = findViewById<Button>(R.id.thb)
//        val ndb = findViewById<Button>(R.id.ndb)
//
//        bu.setOnClickListener {
//            CoroutineScope(Dispatchers.IO).launch {
//                val weather = vm.loadWeather("55.85, 37")
//                runOnUiThread {
//                    Log.d(
//                        "WEATHER_TEST",
//                        (weather.location.name) + "  " +
//                                (weather.current.last_updated) + " " +
//                                (weather.current.temp_c) + " " +
//                                (weather.location.localtime)
//                    )
//                }
//            }
//        }
//        thb.setOnClickListener {
//            CoroutineScope(Dispatchers.IO).launch {
//                val weatherToday = vm.getWeatherTodayHistory("55.85, 37", "2023-01-01")
//                runOnUiThread {
//                    for (item in weatherToday){
//                        Log.d("WEATHER_TEST", item.time + " " + item.temp_c + " " + item.condition.text)
//                    }
//                }
//            }
//        }
//
//        ndb.setOnClickListener {
//            CoroutineScope(Dispatchers.IO).launch {
//                val weatherFuture = vm.getFutureWeatherHistory("55.85, 37", "2023-05-20")
//                runOnUiThread {
//                    for (item in weatherFuture){
//                        Log.d("WEATHER_TEST", item.time + " " + item.temp_c)
//                    }
//                }
//            }
//        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.about -> {
                val intent = Intent(this, AboutActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}