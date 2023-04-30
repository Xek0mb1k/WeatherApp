package com.xekombik.weatherapp.domain

data class ShortWeatherInf(
    val time: String,
    val condition: Condition,
    val temp_c: Double,
    val temp_f: Double,
    val is_day: Boolean,
    val vis_km: Double = 0.0,
    val vis_miles: Double = 0.0,
    val humidity: Double = 0.0,
    val wind_mph: Double = 0.0,
    val wind_kph: Double = 0.0,
    val pressure_mb: Double = 0.0,
)
