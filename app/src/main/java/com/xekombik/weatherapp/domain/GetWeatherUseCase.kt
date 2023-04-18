package com.xekombik.weatherapp.domain

class GetWeatherUseCase(private val weatherRepository: WeatherRepository) {
    suspend fun getWeather(location: String): CurrentWeather{
        return weatherRepository.getWeather(location)
    }
}