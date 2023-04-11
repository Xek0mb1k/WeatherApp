package com.xekombik.weatherapp.domain

interface WeatherRepository {
    fun getWeatherNextDaysHistoryUseCase(): List<ShortWeatherInf>
    fun getWeatherTodayHistoryUseCase(): List<ShortWeatherInf>
    fun getWeather(): Weather
    fun setSettings(useTempCel: Boolean, useKph: Boolean)
}