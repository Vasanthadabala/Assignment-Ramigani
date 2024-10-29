package com.example.assignment_ramigani.screens

import android.app.Application
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(navController: NavController) {
    val context = LocalContext.current
    val application = context.applicationContext as Application

    var searchText by remember { mutableStateOf(TextFieldValue("")) }
    val viewModel: WeatherScreenViewModel = viewModel(factory = WeatherViewModelFactory(application))

    val weatherList by viewModel.allWeatherData.observeAsState()
    val isLoading by viewModel.loading.observeAsState(false)

    Column(
        modifier = Modifier.padding(top = 40.dp, start = 10.dp, end = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WeatherSearchBar(searchText) {
            searchText = it
            viewModel.fetchWeatherData(it.text)
        }

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(48.dp), // Adjust the size here
                    strokeWidth = 4.dp
                )
            }
        } else {
            weatherList?.forEach { weather ->
                Card(
                    elevation = CardDefaults.cardElevation(5.dp),
                    shape = RoundedCornerShape(24),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .clickable {
                            // Use Uri.encode() to safely pass parameters in the route
                            val route = "details/${Uri.encode(weather.city)}/${weather.temperature}" +
                                    "/${Uri.encode(weather.condition)}/${weather.humidity}" +
                                    "/${weather.wind}/${weather.precipitation}" +
                                    "/${Uri.encode(weather.conditionIcon)}"
                            navController.navigate(route)
                        }
                ) {
                    Text(
                        text = "${weather.temperature} °C",
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun WeatherSearchBar(searchText: TextFieldValue, onSearch: (TextFieldValue) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        value = searchText,
        onValueChange = { onSearch(it) },
        singleLine = true,
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = null,
                modifier = Modifier.clickable {
                    onSearch(searchText)
                    keyboardController?.hide()
                }
            )
        },
        placeholder = { Text("Search city (e.g., Goa, IN)") },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}