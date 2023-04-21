package com.xekombik.weatherapp.presentation

import androidx.lifecycle.ViewModel
import com.xekombik.weatherapp.domain.*

class MainViewModel(repository: WeatherRepository) : ViewModel() {

    private val getWeatherUseCase = GetWeatherUseCase(repository)
    private val getWeatherTodayHistoryUseCase = GetWeatherTodayHistoryUseCase(repository)
    private val getFutureWeatherHistoryUseCase = GetFutureWeatherHistoryUseCase(repository)


    suspend fun loadWeather(location: String): CurrentWeather {
        return getWeatherUseCase.getWeather(location)
    }

    suspend fun getWeatherTodayHistory(location: String, data: String): List<ShortWeatherInf> {
        return getWeatherTodayHistoryUseCase.getWeatherTodayHistory(location, data)
    }

    suspend fun getFutureWeatherHistory(location: String, data: String): List<ShortWeatherInf> {
        return getFutureWeatherHistoryUseCase.getWeatherNextDaysHistory(location, data)
    }

}
