package com.example.assignment_ramigani.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.assignment_ramigani.navigation.TopBar

@ExperimentalMaterial3Api
@Composable
fun DetailedWeatherScreen(
    navController: NavHostController, // Include NavHostController
    city: String,
    temperature: String,
    condition: String,
    humidity: String,
    wind: String,
    precipitation: String,
    iconUrl: String
) {
    // Use Scaffold to include the TopBar
    Scaffold(
        topBar = {
            TopBar(name = "Weather Details", navController = navController)
        },
        content = { paddingValues -> // Handle padding from the Scaffold
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues) // Use Scaffold's padding
                    .padding(12.dp)
                    .background(Color.White) // White background
            ) {
                // Card with the same background color as the Celsius data card
                Card(
                    elevation = CardDefaults.cardElevation(3.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)), // Light Red/Pink
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .wrapContentHeight()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // City name
                        Text(
                            text = city,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0277BD) // Dark blue
                        )

                        // Weather icon
                        Image(
                            painter = rememberAsyncImagePainter(model = iconUrl),
                            contentDescription = "Weather Icon",
                            modifier = Modifier.size(80.dp),
                            contentScale = ContentScale.Fit
                        )

                        // Temperature display
                        Text(
                            text = "$temperature °C",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFD32F2F), // Red for temperature
                            textAlign = TextAlign.Center
                        )

                        // Weather condition
                        Text(
                            text = condition,
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(16.dp)) // Space between sections

                        // Additional weather details in a row
                        WeatherDetailRow(label = "Humidity", value = "$humidity%")
                        WeatherDetailRow(label = "Wind", value = "$wind km/h")
                        WeatherDetailRow(label = "Precipitation", value = "$precipitation mm")
                    }
                }
            }
        }
    )
}

@Composable
fun WeatherDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF0288D1) // Blue color for values
        )
    }
}