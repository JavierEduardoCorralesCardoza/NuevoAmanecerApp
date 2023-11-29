package com.example.proyectonuevoamanecer.screens

sealed class AppRoutes(val route: String){
    object LoginScreen: AppRoutes("Login")
    object HomeScreen: AppRoutes("Home")
    object Administracion: AppRoutes("Administracion")
    object MemoramaScreen: AppRoutes("Memorama")
    object MenuMemoramaScreen: AppRoutes("MenuMemorama")
    object EligirImagen: AppRoutes("EligirImagen")
    object MainFlashMenu: AppRoutes("Flashcards")
    object FlashcardDecks: AppRoutes("FlashcardDeck")
    object FlashcardGame: AppRoutes("FlashcardGames")
    object JuegosScreen: AppRoutes("JuegosScreen")
    object NivelesJuegos: AppRoutes("NivelesJuegos")
    object DiferenciasCard: AppRoutes("Diferencias")
    object Rompecabezas: AppRoutes("Rompecabezas")
    object Numeros: AppRoutes("Numeros")
}
