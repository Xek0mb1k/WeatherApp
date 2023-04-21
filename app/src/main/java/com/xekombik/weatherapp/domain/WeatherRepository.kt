package com.xekombik.weatherapp.domain

interface WeatherRepository {
    suspend fun getFutureWeatherHistory(
        location: String,
        data: String
    ): List<ShortWeatherInf>

    suspend fun getWeatherTodayHistory(location: String, data: String): List<ShortWeatherInf>
    suspend fun getWeather(location: String): CurrentWeather
}