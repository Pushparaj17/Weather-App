package com.pushparaj.samples.weatherapp.api

import com.pushparaj.samples.weatherapp.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("/data/2.5/weather")
    suspend fun getWeather(@Query("q") city: String, @Query("appid") appid: String): Response<WeatherResponse>

    @GET("/data/2.5/weather")
    suspend fun getWeather(@Query("lat") lat: String, @Query("lon") lon: String, @Query("appid") appid: String): Response<WeatherResponse>
}