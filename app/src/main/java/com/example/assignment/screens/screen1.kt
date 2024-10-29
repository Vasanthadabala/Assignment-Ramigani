package com.example.assignment.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.assignment.database.WeatherEntity
import com.example.assignment.network.WeatherViewModel

@Composable
fun Screen1(navController: NavHostController, weatherViewModel: WeatherViewModel = viewModel()) {
    var cityName by remember { mutableStateOf("") }
    var weatherData by remember { mutableStateOf<WeatherEntity?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = cityName,
            onValueChange = { cityName = it },
            label = { Text("Enter City Name") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    if (cityName.isNotEmpty()) {
                        weatherViewModel.fetchWeather(cityName)
                    }
                }
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (cityName.isNotEmpty()) {
                weatherViewModel.fetchWeather(cityName)
            }
        }) {
            Text("Get Weather")
        }

        Spacer(modifier = Modifier.height(32.dp))

        weatherData = weatherViewModel.weather
        weatherData?.let {
            WeatherInfo(it)
        } ?: Text("No data available")
    }
}

@Composable
fun WeatherInfo(weather: WeatherEntity) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("City: ${weather.cityName}", style = MaterialTheme.typography.titleMedium)
        Text("Temperature: ${weather.temperature}°C", style = MaterialTheme.typography.bodyMedium)
        Text("Description: ${weather.description}", style = MaterialTheme.typography.bodyMedium)
        Text("Humidity: ${weather.humidity}%", style = MaterialTheme.typography.bodyMedium)
    }
}