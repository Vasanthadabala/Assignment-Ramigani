package com.example.weatherapp.screens

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.api.WeatherRepository
import com.example.weatherapp.data.roomdatabase.WeatherEntity
import kotlinx.coroutines.launch

class WeatherScreenViewModel(application: Application, private val repository: WeatherRepository) : AndroidViewModel(application) {

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun fetchWeatherData(city: String) {
        _loading.value = true
        viewModelScope.launch {
            try {
                Log.d("WeatherScreenViewModel", "Fetching weather data for city: $city")
                val result = repository.fetchWeatherData(city)
                _loading.value = false
                if (result == null) {
                    Log.e("WeatherScreenViewModel", "Failed to fetch weather data for city: $city")
                } else {
                    Log.d("WeatherScreenViewModel", "Successfully fetched weather data for city: $city")
                }
            } catch (e: Exception) {
                Log.e("WeatherScreenViewModel", "Error fetching weather data for city: $city", e)
            }
        }
    }

    fun getWeatherDataByCity(city: String): LiveData<List<WeatherEntity>> {
        Log.d("WeatherScreenViewModel", "Getting weather data for city: $city")
        return repository.getWeatherDataByCity(city)
    }

    val allWeatherData: LiveData<List<WeatherEntity>> = repository.allWeatherData
}