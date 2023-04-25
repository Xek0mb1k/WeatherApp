package com.xekombik.weatherapp.domain

interface WeatherRepository {

    suspend fun getWeatherTodayHistory(location: String, data: String): List<ShortWeatherInf>
    suspend fun getForecast(location: String): List<ShortWeatherInf>
    suspend fun getWeather(location: String): CurrentWeather
}
