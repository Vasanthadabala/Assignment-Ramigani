package com.example.weatherapp.screens

import android.app.Application
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.weatherapp.R

@ExperimentalMaterial3Api
@Composable
fun WeatherScreen() {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val application = context.applicationContext as Application

    var searchText by remember { mutableStateOf(TextFieldValue("")) }

    val viewModel: WeatherScreenViewModel = viewModel(
        factory = WeatherViewModelFactory(application)
    )

    // Fetch weather data every time the search text changes
    val weatherList by viewModel.allWeatherData.observeAsState()

    // Debug log
    Log.d("WeatherScreen", "Weather data: $weatherList")

    val isLoading by viewModel.loading.observeAsState(false)


    Column(
        modifier = Modifier.padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        val containerColor = Color.White
        Card(
            elevation = CardDefaults.cardElevation(5.dp),
            shape = RoundedCornerShape(50),
            colors = CardDefaults.cardColors(containerColor),
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            TextField(
                singleLine = true,
                value = searchText,
                onValueChange = { searchText = it },
                leadingIcon = {
                    Box(
                        modifier = Modifier.padding(start = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                trailingIcon = {
                    Box(
                        modifier = Modifier.padding(end = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "",
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    viewModel.fetchWeatherData(searchText.text)
                                    keyboardController?.hide()
                                }
                        )
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = containerColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
                placeholder = {
                    Row {
                        Text(text = "Search")
                        Text(
                            text = "Ex: Goa, IN",
                            style = TextStyle(color = Color.LightGray),
                            modifier = Modifier.padding(start = 8.dp, top = 2.dp)
                        )
                    }},
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 20.dp, horizontal = 8.dp),
            ) {
                if (weatherList.isNullOrEmpty()) {
                    Text(
                        text = "No weather data available",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W400,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(10.dp)
                    )
                } else {
                    weatherList?.forEach { weather ->
                        Column{
                            Card(
                                elevation = CardDefaults.cardElevation(5.dp),
                                shape = RoundedCornerShape(28),
                                colors = CardDefaults.cardColors(containerColor),
                                modifier = Modifier
                                    .fillMaxWidth()

                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp)
                                ) {
                                    Box(
                                        modifier = Modifier.padding(5.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.LocationOn,
                                            contentDescription = "",
                                            modifier = Modifier.size(22.dp)
                                        )
                                    }
                                    Text(
                                        text = "${weather.city}",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.W500,
                                        modifier = Modifier.padding(5.dp)
                                    )
                                    Text(
                                        text = "${weather.country}",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.W500,
                                        modifier = Modifier.padding(5.dp)
                                    )
                                }
                            }
                            Card(
                                elevation = CardDefaults.cardElevation(5.dp),
                                shape = RoundedCornerShape(10),
                                colors = CardDefaults.cardColors(containerColor),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp)
                            ) {
                                Text(
                                    text = "${weather.temperature} °C",
                                    fontSize = 40.sp,
                                    fontWeight = FontWeight.W500,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .fillMaxWidth()
                                )
                                Box(
                                    modifier = Modifier
                                        .size(100.dp)
                                        .clip(RoundedCornerShape(10))
                                        .align(Alignment.CenterHorizontally)
                                ) {
                                    AsyncImage(
                                        modifier = Modifier
                                            .size(200.dp)
                                            .fillMaxWidth(),
                                        model = "https:${weather.conditionIcon}".replace(
                                            "64x64",
                                            "128x128"
                                        ),
                                        contentDescription = "Condition icon"
                                    )
                                }
                                Text(
                                    text = "${weather.condition}",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.W500,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .fillMaxWidth()
                                )
                            }
                            Column(
                                modifier = Modifier.padding(vertical = 5.dp)
                            ) {
                                Card(
                                    elevation = CardDefaults.cardElevation(5.dp),
                                    shape = RoundedCornerShape(16),
                                    colors = CardDefaults.cardColors(containerColor),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(50.dp)
                                            .clip(RoundedCornerShape(10))
                                            .align(Alignment.CenterHorizontally)
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.humidity),
                                            contentDescription = ""
                                        )
                                    }
                                    Text(
                                        text = "Humidity",
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.W500,
                                        textAlign = TextAlign.Center,
                                        color = Color.DarkGray,
                                        modifier = Modifier
                                            .padding(5.dp)
                                            .fillMaxWidth()
                                    )
                                    Text(
                                        text = "${weather.humidity} %",
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.W500,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .padding(5.dp)
                                            .fillMaxWidth()
                                    )
                                }
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 5.dp)
                                ) {
                                    Card(
                                        elevation = CardDefaults.cardElevation(5.dp),
                                        shape = RoundedCornerShape(24),
                                        colors = CardDefaults.cardColors(containerColor),
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(vertical = 5.dp, horizontal = 3.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(40.dp)
                                                .clip(RoundedCornerShape(10))
                                                .align(Alignment.CenterHorizontally)
                                        ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.wind),
                                                contentDescription = ""
                                            )
                                        }
                                        Text(
                                            text = "Wind",
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.W500,
                                            textAlign = TextAlign.Center,
                                            color = Color.DarkGray,
                                            modifier = Modifier
                                                .padding(3.dp)
                                                .fillMaxWidth()
                                        )
                                        Text(
                                            text = "${weather.wind} km/h",
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.W500,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier
                                                .padding(3.dp)
                                                .fillMaxWidth()
                                        )
                                    }
                                    Card(
                                        elevation = CardDefaults.cardElevation(5.dp),
                                        shape = RoundedCornerShape(24),
                                        colors = CardDefaults.cardColors(containerColor),
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(vertical = 5.dp, horizontal = 3.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(40.dp)
                                                .clip(RoundedCornerShape(10))
                                                .align(Alignment.CenterHorizontally)
                                        ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.percipitation),
                                                contentDescription = ""
                                            )
                                        }
                                        Text(
                                            text = "Precipitation",
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.W500,
                                            textAlign = TextAlign.Center,
                                            color = Color.DarkGray,
                                            modifier = Modifier
                                                .padding(3.dp)
                                                .fillMaxWidth()
                                        )
                                        Text(
                                            text = "${weather.precipitation} mm",
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.W500,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier
                                                .padding(3.dp)
                                                .fillMaxWidth()
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}