package com.pushparaj.samples.weatherapp.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.pushparaj.samples.weatherapp.Constants
import com.pushparaj.samples.weatherapp.model.WeatherResponse
import com.pushparaj.samples.weatherapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val app:Application, private val repository: WeatherRepository) : AndroidViewModel(app) {

    private lateinit var editor: SharedPreferences.Editor
    private val _response = MutableLiveData<WeatherResponse>()
    val weatherResponse: LiveData<WeatherResponse>
        get() = _response

    init {
        val sharedPreferences = getApplication<Application>().getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
        val city: String? = sharedPreferences.getString(Constants.SEARCH_CITY_NAME, "")
        editor = sharedPreferences.edit()
        getWeather(city?:"")
    }

    fun getWeather(city: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.getWeather(city).let { response ->
            if (response.isSuccessful) {
                _response.postValue(response.body())
                editor.putString(Constants.SEARCH_CITY_NAME, city)
                editor.apply()
            } else {
                Log.d("ViewModel", "Error Code : ${response.errorBody()}")
                Toast.makeText(getApplication<Application>().applicationContext, "Please enter valid city name", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun updatePreference(city: String) {

    }
}
