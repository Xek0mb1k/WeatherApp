package com.xekombik.weatherapp.domain

class GetWeatherTodayHistoryUseCase(private val weatherRepository: WeatherRepository) {
    suspend fun getWeatherTodayHistory(location: String, data: String): List<ShortWeatherInf>{
        return weatherRepository.getWeatherTodayHistory(location, data)
    }
}
