package com.example.proyectonuevoamanecer.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectonuevoamanecer.MainActivity
import com.example.proyectonuevoamanecer.screens.home.HomeScreen
import com.example.proyectonuevoamanecer.screens.juegos.memorama.MemoramaScreen

@Composable
fun Navegacion(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppRoutes.HomeScreen.route){
        composable(AppRoutes.HomeScreen.route) { HomeScreen(navController) }
        composable(AppRoutes.MemoramaScreen.route) { MemoramaScreen(navController) }
    }
}