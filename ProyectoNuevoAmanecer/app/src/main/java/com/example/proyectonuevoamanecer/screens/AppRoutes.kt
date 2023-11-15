package com.example.proyectonuevoamanecer.screens

sealed class AppRoutes(val route: String){
    object LoginScreen: AppRoutes("Login")
    object HomeScreen: AppRoutes("Home")
    object MemoramaScreen: AppRoutes("Memorama")
    object MainFlashMenu: AppRoutes("Flashcards")
    object FlashcardDecks: AppRoutes("FlashcardDeck")
    object FlashcardGame: AppRoutes("FlashcardGames")
    object JuegosScreen: AppRoutes("JuegosScreen")
    object NivelesJuegos: AppRoutes("NivelesJuegos")
    object  DiferenciasCard: AppRoutes("Diferencias")
    object Numeros: AppRoutes("Numeros")
}
