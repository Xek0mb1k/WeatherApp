package com.xekombik.weatherapp.data

import com.xekombik.weatherapp.domain.CurrentWeather
import com.xekombik.weatherapp.domain.FutureWeather
import com.xekombik.weatherapp.domain.Places
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApi {
    @GET("v1/current.json")
    suspend fun getWeather(
        @Query("key") key: String,
        @Query("q") location: String,
        @Query("aqi") value: String
    ): CurrentWeather

    @GET("v1/forecast.json")
    suspend fun getForecast(
        @Query("key") key: String,
        @Query("q") location: String,
        @Query("days") days: Int,
        @Query("aqi") aqi: String,
        @Query("alerts") alerts: String

    ): FutureWeather

    @GET("v1/search.json")
    suspend fun getPlaces(
        @Query("key") key: String,
        @Query("q") searchRequest: String

    ): Places

}
