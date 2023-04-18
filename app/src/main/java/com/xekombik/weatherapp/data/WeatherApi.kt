package com.xekombik.weatherapp.data

import com.xekombik.weatherapp.domain.CurrentWeather
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApi {
    @GET("v1/current.json")
    suspend fun getWeather(
        @Query("key") name: String,
        @Query("q") location: String,
        @Query("aqi") value: String
    ): CurrentWeather
}
