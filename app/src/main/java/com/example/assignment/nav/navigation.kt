package com.example.assignment.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.assignment.screens.Screen1
import com.example.assignment.screens.Screen2

@Composable
fun MyNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = screen1.route){
        composable(screen1.route){
            Screen1(navController)
        }

        composable(screen1.route){
            Screen2(navController)
        }
    }
}