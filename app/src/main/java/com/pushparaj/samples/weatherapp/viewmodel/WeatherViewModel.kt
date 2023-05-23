package com.pushparaj.samples.weatherapp.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.*
import com.pushparaj.samples.weatherapp.Constants
import com.pushparaj.samples.weatherapp.R
import com.pushparaj.samples.weatherapp.model.WeatherResponse
import com.pushparaj.samples.weatherapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val app:Application, private val repository: WeatherRepository) : AndroidViewModel(app) {

    private lateinit var editor: SharedPreferences.Editor
    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState>
        get() = _viewState

    init {
        val sharedPreferences = getApplication<Application>().getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
        val city: String? = sharedPreferences.getString(Constants.SEARCH_CITY_NAME, null)
        editor = sharedPreferences.edit()
        // Using DEFAULT_CITY is work around. best solution is using the user's current location
        getWeather(city?: Constants.DEFAULT_CITTY)
    }

    fun getWeather(city: String) = viewModelScope.launch(Dispatchers.IO) {
        _viewState.postValue(ViewState.Loading)
        repository.getWeather(city).let { response ->
            if (response.isSuccessful) {
                _viewState.postValue(ViewState.Content(response.body()))
                editor.putString(Constants.SEARCH_CITY_NAME, city)
                editor.apply()
            } else {
                Log.d("ViewModel", "Error : ${response.errorBody()}")
                _viewState.postValue(ViewState.Error(getApplication<Application>().getString(R.string.error_message)))
            }
        }
    }

    sealed class ViewState {
        object Loading : ViewState()
        data class Error(val message: String) : ViewState()
        data class Content(val weatherResponse: WeatherResponse?) : ViewState()
    }

}
