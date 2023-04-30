package com.xekombik.weatherapp.presentation


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity

import com.xekombik.weatherapp.databinding.ActivityWeatherDetailBinding
import com.xekombik.weatherapp.databinding.HourlyCardViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Locale


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

    override fun onResume() {
        super.onResume()
        setData()
    }

    @SuppressLint("SetTextI18n")
    private fun setData() {
        val location = intent.getStringExtra("location")!!
        val dayPosition = intent.getIntExtra("day_position", 0)
        val data = intent.getStringExtra("data")!!
        CoroutineScope(Dispatchers.IO).launch {

            val hourlyData = vm.getHourlyForecast(location, data)
            val forecastData = vm.getForecast(location)

            runOnUiThread {
                val sharedPreferences =
                    androidx.preference.PreferenceManager.getDefaultSharedPreferences(this@WeatherDetailActivity)
                val tempUnits = sharedPreferences.getString("temp_units", "ºC")
                val speedUnits = sharedPreferences.getString("speed_units", "km/h")

                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

                val date = dateFormat.parse(forecastData[dayPosition].time)

                val formattedDateFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.ENGLISH)

                binding.locationTextView.text = location

                binding.weatherDetailDataTextView.text =
                    date.let { formattedDateFormat.format(it!!) }

                binding.weatherDetailIconImageView.setImageResource(
                    getImageResource(
                        forecastData[dayPosition].condition.icon,
                        'd'
                    )
                )

                if (tempUnits == "ºC") {
                    binding.weatherDetailDegreesTextView.text =
                        forecastData[dayPosition].temp_c.toInt().toString() + " ºC"
                } else {
                    binding.weatherDetailDegreesTextView.text =
                        forecastData[dayPosition].temp_f.toInt().toString() + " ºF"
                }
                binding.weatherDetailCloudDetailTextView.text =
                    forecastData[dayPosition].condition.text

                binding.hourlyWeatherLL.removeAllViews()
                for (hourlyItem in hourlyData){
                    val vB = HourlyCardViewBinding.inflate(
                        LayoutInflater.from(this@WeatherDetailActivity),
                        binding.hourlyWeatherLL,
                        false
                    )

                    val tempValue =
                        if (tempUnits == "ºC") hourlyItem.temp_c.toInt() else hourlyItem.temp_f.toInt()

                    val stateLetter = if (hourlyItem.is_day)
                        'd'
                    else
                        'n'

                    vB.weatherHourlyImageView.setImageResource(
                        getImageResource(
                            hourlyItem.condition.icon,
                            stateLetter
                        )
                    )
                    vB.hourlyTemperatureTextView.text = "${tempValue}${tempUnits!![0]}"
                    vB.hourlyTimeTextView.text = hourlyItem.time.split(" ")[1]

                    binding.hourlyWeatherLL.addView(vB.root)
                }

                binding.humidityPercentageTextView.text =
                    forecastData[dayPosition].humidity.toInt().toString() + " %"
                binding.airPressureValueTextView.text =
                    forecastData[dayPosition].pressure_mb.toInt().toString() + " hPa"


                if (speedUnits == "km/h") {
                    binding.windSpeedTextView.text =
                        forecastData[dayPosition].wind_kph.toInt().toString() + " km/h"
                    binding.visibilityPercentageTextView.text =
                        forecastData[dayPosition].vis_km.toInt().toString() + " km"
                } else {
                    binding.windSpeedTextView.text =
                        forecastData[dayPosition].wind_mph.toInt().toString() + " mi/h"
                    binding.visibilityPercentageTextView.text =
                        forecastData[dayPosition].vis_miles.toInt().toString() + " mi"
                }


            }
        }
    }

    @SuppressLint("DiscouragedApi")
    fun getImageResource(icon: String, stateLetter: Char): Int {
        val parts = icon.split("/")
        val imageName = stateLetter + parts.last().split(".")[0]
        return resources.getIdentifier(imageName, "drawable", packageName)
    }


}