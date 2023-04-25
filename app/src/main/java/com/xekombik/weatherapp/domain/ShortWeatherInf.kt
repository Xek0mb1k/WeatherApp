package com.xekombik.weatherapp.domain

data class ShortWeatherInf(
    val time: String,
    val condition: Condition,
    val temp_c: Double,
    val temp_f: Double,
    val is_day: Boolean
)
