package com.xekombik.weatherapp.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import com.xekombik.weatherapp.domain.Current
import com.xekombik.weatherapp.domain.CurrentWeather
import com.xekombik.weatherapp.domain.GetWeatherUseCase
import com.xekombik.weatherapp.domain.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val repository: WeatherRepository): ViewModel() {

    private val getWeatherUseCase = GetWeatherUseCase(repository)

    lateinit var currentWeather: CurrentWeather

    fun loadWeather(location : String) {
        CoroutineScope(Dispatchers.IO).launch{
            val result = getWeatherUseCase.getWeather(location)
            currentWeather = result
            withContext(Dispatchers.Main) {
                val weather = currentWeather
                Log.d(
                    "WEATHER_TEST",
                    (weather.location.region) + "  " + (weather.current.last_updated) +
                            " " + (weather.current.temp_c)
                )
            }
        }
    }


    fun getWeather(): CurrentWeather = currentWeather
}
