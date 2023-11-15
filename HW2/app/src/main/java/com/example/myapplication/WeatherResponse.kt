package com.example.myapplication

data class WeatherResponse(
    val current: Current
)

data class Current(
    val temp_c: Float
)