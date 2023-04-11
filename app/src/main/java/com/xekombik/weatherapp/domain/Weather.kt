package com.xekombik.weatherapp.domain

data class Weather(
    val location: String,
    val temperature: Float,
    val useTempCel: Boolean,

    val last_updated: String,
    val is_day: Boolean,
    val condition: String,

    val useKph: Boolean,

    val humidity: Int,
    val wind: String,
    val pressure_mb: Float,
    val visibility: Float,
)
