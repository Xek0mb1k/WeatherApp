package com.xekombik.weatherapp.domain

class GetWeatherUseCase(private val weatherRepository: WeatherRepository) {
    fun getWeather(): Weather{
        return weatherRepository.getWeather()
    }
}