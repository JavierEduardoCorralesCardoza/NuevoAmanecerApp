package com.example.proyectonuevoamanecer.screens

sealed class AppRoutes(val route: String){
    object HomeScreen: AppRoutes("Home")
    object MemoramaScreen: AppRoutes("Memorama")
}
