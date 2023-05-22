package com.pushparaj.samples.weatherapp.model

data class WeatherResponse(
    val name:String,
    val coord: Coord,
    val weather: List<Weather>,
    val main: Main,
    val wind: Wind
    )

data class Weather(val id: String,
                   val main: String,
                   val description: String,
                   val icon: String
                   )

data class Wind(val speed: String,
                val deg: String,
                val gust: String)

data class Coord(val lon: String,
                val lat: String)

data class Main(val temp: String,
                val feels_like: String,
                val temp_min: String,
                val temp_max: String,
                val humidity: String,
                val pressure: String
                )