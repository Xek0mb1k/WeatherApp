package com.xekombik.weatherapp.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import com.xekombik.weatherapp.R
import com.xekombik.weatherapp.databinding.ActivityMainBinding
import com.xekombik.weatherapp.domain.CurrentWeather
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private val vm by viewModel<MainViewModel>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        setSupportActionBar(binding.activityMainToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)


        binding.locationTextView.setOnClickListener {
            val intent = Intent(this, ChangeLocationActivity::class.java)
            startActivity(intent)
        }

        CoroutineScope(Dispatchers.IO).launch {
            val weather = vm.loadWeather("Moscow")

            runOnUiThread {
                refreshData(weather)
            }
        }
    }


    @SuppressLint("SetTextI18n", "DiscouragedApi")
    fun refreshData(weather: CurrentWeather) {

        val stateLetter: Char
        if (weather.current.is_day == 1) {
            binding.mainCardLayout.background = ContextCompat.getDrawable(
                this, R.drawable.day_linear_gradient
            )
            stateLetter = 'd'
        } else {
            binding.mainCardLayout.background = ContextCompat.getDrawable(this,
                R.drawable.night_linear_gradient)
            stateLetter = 'n'
        }

        binding.locationTextView.text = weather.location.name

        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH)
        val date = dateFormat.parse(weather.current.last_updated)

        val formattedDateFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.ENGLISH)
        val formattedDate = date?.let { formattedDateFormat.format(it) }
        binding.todayDataTextView.text = formattedDate

        val parts = weather.current.condition.icon.split("/")
        val imageName = stateLetter + parts.last().split(".")[0]

        binding.weatherImageView.setImageResource(
            resources.getIdentifier(imageName, "drawable", packageName)
        )

        val tempUnits = sharedPreferences.getString("temp_units", "º C")
        if (tempUnits == "ºC") {
            binding.weatherTodayTemperature.text = "${weather.current.temp_c}$tempUnits"
        } else
            binding.weatherTodayTemperature.text = "${weather.current.temp_f}$tempUnits"

        binding.weatherTodayWeather.text = weather.current.condition.text


        binding.lastUpdatedDataTextView.text =
            "Last updated ${
                weather.current.last_updated
                    .split(" ")[1]
                    .replace(":", ".")
            }"

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