package com.xekombik.weatherapp.domain

class GetHourlyForecastUseCase(private val weatherRepository: WeatherRepository) {
    suspend fun getHourlyForecast(location: String, data: String): List<ShortWeatherInf>{
        return weatherRepository.getHourlyForecast(location, data)
    }
}
