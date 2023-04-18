package com.xekombik.weatherapp.domain

interface WeatherRepository {
    fun getWeatherNextDaysHistoryUseCase(): List<ShortWeatherInf>
    fun getWeatherTodayHistoryUseCase(): List<ShortWeatherInf>
    suspend fun getWeather(location: String): CurrentWeather
}