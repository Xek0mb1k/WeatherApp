package com.xekombik.weatherapp.domain

interface WeatherRepository {

    suspend fun getHourlyForecast(location: String, data: String): List<ShortWeatherInf>
    suspend fun getForecast(location: String): List<ShortWeatherInf>
    suspend fun getWeather(location: String): CurrentWeather
    suspend fun getCityList(city: String): List<String>
}
