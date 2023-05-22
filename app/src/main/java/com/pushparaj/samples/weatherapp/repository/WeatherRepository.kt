package com.pushparaj.samples.weatherapp.repository

import com.pushparaj.samples.weatherapp.Constants
import com.pushparaj.samples.weatherapp.api.WeatherService
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val apiService: WeatherService) {
    suspend fun getWeather(city: String) = apiService.getWeather(city, Constants.API_KEY)
}