package com.xekombik.weatherapp.domain

class GetWeatherTodayHistoryUseCase(private val weatherRepository: WeatherRepository) {
    fun getWeatherTodayHistoryUseCase(): List<ShortWeatherInf>{
        return weatherRepository.getWeatherTodayHistoryUseCase()
    }
}