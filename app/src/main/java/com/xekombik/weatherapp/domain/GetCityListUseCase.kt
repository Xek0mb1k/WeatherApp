package com.xekombik.weatherapp.domain

class GetCityListUseCase(private val weatherRepository: WeatherRepository) {
    suspend fun getCityList(city: String): List<String> {
        return weatherRepository.getCityList(city)
    }
}