package com.example.proyectonuevoamanecer.screens

import DiferenciasCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.juego1jetpc.ui.EligirImagen

import com.example.proyectonuevoamanecer.screens.flashcards.FlashViewModel
import com.example.proyectonuevoamanecer.screens.flashcards.FlashViewModelFactory
import com.example.proyectonuevoamanecer.screens.flashcards.FlashcardDatabase

import com.example.proyectonuevoamanecer.screens.juegos.numeros.Numeros

import com.example.proyectonuevoamanecer.screens.flashcards.FlashcardDecks
import com.example.proyectonuevoamanecer.screens.flashcards.FlashcardGame
import com.example.proyectonuevoamanecer.screens.flashcards.MainFlashMenu
import com.example.proyectonuevoamanecer.screens.home.HomeScreen
import com.example.proyectonuevoamanecer.screens.home.LoginScreen
import com.example.proyectonuevoamanecer.screens.juegos.JuegosScreen
import com.example.proyectonuevoamanecer.screens.juegos.NivelesJuegos
import com.example.proyectonuevoamanecer.screens.juegos.memorama.MemoramaScreen
import com.example.proyectonuevoamanecer.screens.juegos.rompecabezas.Rompecabezas

@Composable
fun Navegacion(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppRoutes.LoginScreen.route) {
        composable(AppRoutes.LoginScreen.route) { LoginScreen(navController) }
        composable(AppRoutes.HomeScreen.route) { HomeScreen(navController) }
        composable(AppRoutes.MainFlashMenu.route) { MainFlashMenu(navController) }
        composable(AppRoutes.FlashcardDecks.route) { FlashcardDecks(navController) }
        composable(AppRoutes.Numeros.route) { Numeros(navController) }
        composable(AppRoutes.EligirImagen.route) { EligirImagen(navController) }
        composable(AppRoutes.Rompecabezas.route) { Rompecabezas(navController) }
        composable(
            route = AppRoutes.FlashcardGame.route + "/{mazoId}",
            arguments = listOf(navArgument("mazoId") {
                type = NavType.IntType

            })
        ) { backStackEntry ->
            val viewModel: FlashViewModel = viewModel()
            val mazoId = backStackEntry.arguments?.getInt("mazoId")
            if (mazoId != null) {
                FlashcardGame(navController, mazoId, viewModel)
                val context = LocalContext.current
                val database = FlashcardDatabase.getInstance(context)
                val viewModel: FlashViewModel = viewModel(factory = FlashViewModelFactory(database))
                val mazo = backStackEntry.arguments?.getString("mazo")
                if (mazo != null) {
                    FlashcardGame(navController, mazoId, viewModel)
                }
            }
            composable(AppRoutes.JuegosScreen.route) { JuegosScreen(navController) }
            composable(AppRoutes.NivelesJuegos.route) {
                NivelesJuegos(
                    navController,
                    AppRoutes.NivelesJuegos.route
                )
            }

            composable(AppRoutes.DiferenciasCard.route) { DiferenciasCard(navController, 1) }

            composable(AppRoutes.DiferenciasCard.route) { DiferenciasCard(navController, 1) }

            composable(
                route = AppRoutes.NivelesJuegos.route + "/{ruta}",
                arguments = listOf(navArgument("ruta") {
                    type = NavType.StringType
                    defaultValue = ""
                })
            ) { backStackEntry ->
                val ruta = backStackEntry.arguments?.getString("ruta")
                NivelesJuegos(navController, ruta ?: "")
            }

            composable(AppRoutes.DiferenciasCard.route) { DiferenciasCard(navController, 1) }

            composable(AppRoutes.DiferenciasCard.route) { DiferenciasCard(navController, 1) }

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

            composable(
                route = AppRoutes.DiferenciasCard.route + "/{nivel}",
                arguments = listOf(navArgument("nivel") {
                    type = NavType.IntType
                    defaultValue = 1
                })
            ) { backStackEntry ->
                val nivel = backStackEntry.arguments?.getInt("nivel")
                DiferenciasCard(navController = navController, lvl = nivel ?: 0)
            }
        }
    }
}