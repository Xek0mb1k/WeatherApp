package com.xekombik.weatherapp.domain

class GetWeatherNextDaysHistoryUseCase(private val weatherRepository: WeatherRepository) {
    fun getWeatherNextDaysHistoryUseCase(): List<ShortWeatherInf> {
        return weatherRepository.getWeatherNextDaysHistoryUseCase()
    }
}