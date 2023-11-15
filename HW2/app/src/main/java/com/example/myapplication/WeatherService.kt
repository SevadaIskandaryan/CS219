package com.example.myapplication

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("current.json")
    suspend fun fetchWeather(
        @Query("q") q: String,
        @Query("key") apiKey: String = RetrofitHelper.API_KEY
    ): WeatherResponse?
}