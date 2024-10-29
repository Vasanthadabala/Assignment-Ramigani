package com.example.assignment_ramigani.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.assignment_ramigani.screens.DetailedWeatherScreen
import com.example.assignment_ramigani.screens.WeatherScreen
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@ExperimentalMaterial3Api
@Composable
fun MyNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "WeatherScreen"
    ) {
        composable("WeatherScreen") {
            WeatherScreen(navController)
        }

        composable(
            route = "details/{city}/{temperature}/{condition}/{humidity}/{wind}/{precipitation}/{iconUrl}",
            arguments = listOf(
                navArgument("city") { type = NavType.StringType },
                navArgument("temperature") { type = NavType.StringType },
                navArgument("condition") { type = NavType.StringType },
                navArgument("humidity") { type = NavType.StringType },
                navArgument("wind") { type = NavType.StringType },
                navArgument("precipitation") { type = NavType.StringType },
                navArgument("iconUrl") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            // Safely decode parameters in case they contain special characters
            val city = URLDecoder.decode(
                backStackEntry.arguments?.getString("city").orEmpty(),
                StandardCharsets.UTF_8.toString()
            )
            val temperature = backStackEntry.arguments?.getString("temperature").orEmpty()
            val condition = URLDecoder.decode(
                backStackEntry.arguments?.getString("condition").orEmpty(),
                StandardCharsets.UTF_8.toString()
            )
            val humidity = backStackEntry.arguments?.getString("humidity").orEmpty()
            val wind = backStackEntry.arguments?.getString("wind").orEmpty()
            val precipitation = backStackEntry.arguments?.getString("precipitation").orEmpty()
            val iconUrl = URLDecoder.decode(
                backStackEntry.arguments?.getString("iconUrl").orEmpty(),
                StandardCharsets.UTF_8.toString()
            )

            DetailedWeatherScreen(
                city = city,
                temperature = temperature,
                condition = condition,
                humidity = humidity,
                wind = wind,
                precipitation = precipitation,
                iconUrl = iconUrl,
                navController = navController
            )
        }
    }
}