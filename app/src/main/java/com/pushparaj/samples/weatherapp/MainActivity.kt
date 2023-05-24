package com.pushparaj.samples.weatherapp

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.pushparaj.samples.weatherapp.databinding.ActivityMainBinding
import com.pushparaj.samples.weatherapp.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    //private val LOCATION_PERMISSION_REQ_CODE = 1000;

    //private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var binding: ActivityMainBinding
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /** This is required for finding current location **/
        //fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        //getCurrentLocation()

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.getWeather(query?:"")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        viewModel.viewState.observe(this) { viewState ->
            updateUI(viewState)
        }
    }

    private fun updateUI(viewState: WeatherViewModel.ViewState) {
        binding.apply {
            when (viewState) {
                is WeatherViewModel.ViewState.Loading -> {
                    pBLoading.visibility = View.VISIBLE
                    tvErrorMessage.visibility= View.INVISIBLE
                }
                is WeatherViewModel.ViewState.Content -> {
                    pBLoading.visibility = View.GONE
                    val weatherResponse = viewState.weatherResponse
                    weatherResponse?.let {
                        tvCityName.text = weatherResponse.name
                        tvDescription.text = viewState.weatherResponse.weather[0].description
                        tvTemperature.text = String.format(
                            "%.1f",
                            convertToCelcius(viewState.weatherResponse.main.temp.toDouble())
                        ) + "\u00B0" + " C"
                        tvFeelsLike.text = getString(R.string.feels_like) + " " + String.format(
                            "%.1f",
                            convertToCelcius(viewState.weatherResponse.main.feels_like.toDouble())
                        ) + "\u00B0" + " C"

                        Glide.with(applicationContext)
                            .load(Constants.IMAGE_URL + viewState.weatherResponse.weather[0].icon + "@2x.png")
                            .override(100, 200)
                            .centerCrop() // scale to fill the ImageView and crop any extra
                            .into(iVIcon)
                    }
                }
                is WeatherViewModel.ViewState.Error -> {
                    pBLoading.visibility = View.GONE
                    tvErrorMessage.text = viewState.message
                    tvErrorMessage.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun convertToCelcius(temp: Double): Double {
        return temp - 273.15
    }

    /*private fun getCurrentLocation() {
        // checking location permission
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // request permission
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQ_CODE);
            return
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener {
                val latitude = it.latitude
                val longitude = it.longitude
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed on getting current location",
                    Toast.LENGTH_SHORT).show()
            }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_PERMISSION_REQ_CODE -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted
                } else {
                    // permission denied
                    Toast.makeText(this, "You need to grant permission to access location",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }*/

}