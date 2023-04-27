package com.xekombik.weatherapp.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.xekombik.weatherapp.R
import com.xekombik.weatherapp.databinding.ActivityMainBinding
import com.xekombik.weatherapp.databinding.DailyCardViewBinding
import com.xekombik.weatherapp.databinding.HourlyCardViewBinding
import com.xekombik.weatherapp.domain.CurrentWeather
import com.xekombik.weatherapp.domain.ShortWeatherInf
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
        sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this)

        setSupportActionBar(binding.activityMainToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)


        binding.locationTextView.setOnClickListener {
            val intent = Intent(this, ChangeLocationActivity::class.java)
            startActivity(intent)
        }

        binding.lastUpdatedDataTextView.setOnClickListener {
            startRefresh()
        }

        // TODO("CREATE INTENT TO WEATHER DETAIL ACTIVITY")

    }

    override fun onResume() {
        super.onResume()
        startRefresh()
    }

    private fun startRefresh() {
        val location = "Moscow"
        val todayData = SimpleDateFormat(
            "yyyy-MM-dd",
            Locale.getDefault()
        ).format(Calendar.getInstance().time)

        CoroutineScope(Dispatchers.IO).launch {

            val weather = vm.loadWeather(location)
            val hourlyData = vm.getHourlyForecast(location, todayData)
            val forecastData = vm.getForecast(location)

            runOnUiThread {
                refreshData(weather, hourlyData, forecastData)
            }
        }
    }


    @SuppressLint("SetTextI18n")
    private fun refreshData(
        weather: CurrentWeather,
        hourlyData: List<ShortWeatherInf>,
        forecastData: List<ShortWeatherInf>
    ) {
        var stateLetter = getStateLetterAndSetBackground(weather)
        val tempUnits = sharedPreferences.getString("temp_units", "º C")
        with(binding) {


            locationTextView.text = weather.location.name

            todayDataTextView.text = formatDate(weather)

            weatherImageView.setImageResource(
                getImageResource(weather.current.condition.icon, stateLetter)
            )

            setTodayTemp(weather, tempUnits!!)

            lastUpdatedDataTextView.text =
                "Last updated ${weather.current.last_updated.split(" ")[1]}"
        }


        binding.hourlyWeatherLL.removeAllViews()
        for (hourlyElement in hourlyData) {
            val vB = HourlyCardViewBinding.inflate(
                LayoutInflater.from(this),
                binding.hourlyWeatherLL,
                false
            )
            val tempValue = if (tempUnits == "ºC") hourlyElement.temp_c.toInt() else hourlyElement.temp_f.toInt()

            stateLetter = if (hourlyElement.is_day)
                'd'
            else
                'n'


            vB.weatherHourlyImageView.setImageResource(getImageResource(hourlyElement.condition.icon, stateLetter))
            vB.hourlyTemperatureTextView.text = "${tempValue}${tempUnits!![0]}"
            vB.hourlyTimeTextView.text = hourlyElement.time.split(" ")[1]

            binding.hourlyWeatherLL.addView(vB.root)
        }


        binding.fromDailyListLL.removeAllViews()
        for (dayPosition in forecastData.indices){
            val vB = DailyCardViewBinding.inflate(
                LayoutInflater.from(this),
                binding.fromDailyListLL,
                false
            )
            val tempValue = if (tempUnits == "ºC") forecastData[dayPosition].temp_c.toInt() else forecastData[dayPosition].temp_f.toInt()
            stateLetter = if (forecastData[dayPosition].is_day)
                'd'
            else
                'n'

            vB.dailyWeatherIconImageView.setImageResource(getImageResource(forecastData[dayPosition].condition.icon, stateLetter))

            if (dayPosition == 0)
                vB.dayOnTheWeekTextView.text = "Today"
            else
                vB.dayOnTheWeekTextView.text = getDayOfTheWeek(forecastData[dayPosition].time)

            vB.weatherStateTextView.text = forecastData[dayPosition].condition.text

            vB.dailyTemperatureTextView.text = "${tempValue}${tempUnits!![0]}"

            vB.currentDayCardView.setOnClickListener {

            }

            binding.fromDailyListLL.addView(vB.root)
        }


    }



    private fun formatDate(weather: CurrentWeather): String? {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH)
        val date = dateFormat.parse(weather.current.last_updated)

        val formattedDateFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.ENGLISH)

        return date?.let { formattedDateFormat.format(it) }
    }

    private fun getStateLetterAndSetBackground(weather: CurrentWeather): Char {
        val stateLetter: Char
        if (weather.current.is_day == 1) {
            binding.mainCardLayout.background = ContextCompat.getDrawable(
                this, R.drawable.day_linear_gradient
            )
            stateLetter = 'd'
        } else {
            binding.mainCardLayout.background = ContextCompat.getDrawable(
                this,
                R.drawable.night_linear_gradient
            )
            stateLetter = 'n'
        }
        return stateLetter
    }

    @SuppressLint("DiscouragedApi")
    private fun getImageResource(icon: String, stateLetter: Char): Int {
        val parts = icon.split("/")
        val imageName = stateLetter + parts.last().split(".")[0]
        return resources.getIdentifier(imageName, "drawable", packageName)
    }

    @SuppressLint("SetTextI18n")
    private fun setTodayTemp(weather: CurrentWeather, tempUnits: String) {

        if (tempUnits == "ºC") {
            binding.weatherTodayTemperature.text = "${weather.current.temp_c.toInt()}$tempUnits"
        } else
            binding.weatherTodayTemperature.text = "${weather.current.temp_f.toInt()}$tempUnits"

        binding.weatherTodayWeather.text = weather.current.condition.text
    }

    private fun getDayOfTheWeek(date: String): String {

        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val calendar = Calendar.getInstance()
        calendar.time = formatter.parse(date) as Date

        return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH) as String

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