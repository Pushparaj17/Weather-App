package com.pushparaj.samples.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.pushparaj.samples.weatherapp.databinding.ActivityMainBinding
import com.pushparaj.samples.weatherapp.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: WeatherViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.getWeather(query?:"")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        viewModel.weatherResponse.observe(this, { weather ->
            binding.apply {
                tvCityName.text = weather.name
                tvDescription.text = weather.weather[0].description
                tvTemperature.text = String.format("%.1f",convertToCelcius(weather.main.temp.toDouble())) + "\u00B0" + " C"
                tvFeelsLike.text = "Feels like " + String.format("%.1f",convertToCelcius(weather.main.feels_like.toDouble())) + "\u00B0" + " C"

                Glide.with(applicationContext)
                    .load(Constants.IMAGE_URL + weather.weather[0].icon + "@2x.png")
                    .override(100, 200)
                    .centerCrop() // scale to fill the ImageView and crop any extra
                    .into(binding.imageView)
            }
        })

    }
    private fun convertToCelcius(temp: Double): Double {
        return temp - 273.15
    }
}