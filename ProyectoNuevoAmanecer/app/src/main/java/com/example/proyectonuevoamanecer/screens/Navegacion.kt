package com.example.proyectonuevoamanecer.screens

import DiferenciasCard
import ImageCard
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.proyectonuevoamanecer.MainActivity
import com.example.proyectonuevoamanecer.clases.Mazos
import com.example.proyectonuevoamanecer.screens.flashcards.FlashcardDecks
import com.example.proyectonuevoamanecer.screens.flashcards.FlashcardGame
import com.example.proyectonuevoamanecer.screens.flashcards.MainFlashMenu
import com.example.proyectonuevoamanecer.screens.home.HomeScreen
import com.example.proyectonuevoamanecer.screens.juegos.JuegosScreen
import com.example.proyectonuevoamanecer.screens.juegos.memorama.MemoramaScreen
import com.example.proyectonuevoamanecer.screens.juegos.memorama.NivelesMemorama

@Composable
fun Navegacion(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppRoutes.HomeScreen.route){
        composable(AppRoutes.HomeScreen.route) { HomeScreen(navController) }
        composable(AppRoutes.MainFlashMenu.route) { MainFlashMenu(navController) }
        composable(AppRoutes.FlashcardDecks.route) { FlashcardDecks(navController) }
        composable(route = AppRoutes.FlashcardGame.route,
                    arguments = listOf(navArgument("deck"){
                        type = NavType.StringType
                        defaultValue = "defualt_value"
                    })
        ) { backStackEntry ->
            val deck = backStackEntry.arguments?.getString("deck")
            FlashcardGame(navController = navController, deck = deck ?: "") }
        composable(AppRoutes.JuegosScreen.route) { JuegosScreen(navController) }
        composable(AppRoutes.NivelesMemorama.route) { NivelesMemorama(navController) }
        composable(AppRoutes.DiferenciasCard.route) {DiferenciasCard(navController)}
        composable(
            route = AppRoutes.MemoramaScreen.route + "/{nivel}",
            arguments = listOf(navArgument("nivel") {
                type = NavType.IntType
                defaultValue = 1
            })
        ) { backStackEntry ->
            val nivel = backStackEntry.arguments?.getInt("nivel")
            MemoramaScreen(navController = navController, nivel = nivel ?: 0)
        }
    }
}