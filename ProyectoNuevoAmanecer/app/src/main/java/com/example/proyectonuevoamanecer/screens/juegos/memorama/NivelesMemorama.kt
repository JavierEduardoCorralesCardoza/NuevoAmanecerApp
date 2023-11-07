package com.example.proyectonuevoamanecer.screens.juegos.memorama

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.proyectonuevoamanecer.screens.AppRoutes

@Composable
fun NivelesMemorama(navController: NavController){
    BodyContentNivelesMemorama(navController)
}

@Composable
fun BodyContentNivelesMemorama(navController: NavController){
    Column {
        Text(text = "Niveles")
        Button(onClick = { navController.navigate("${AppRoutes.MemoramaScreen.route}/1") }) {
            Text(text = "Nivel 1")
        }
        Button(onClick = { navController.navigate("${AppRoutes.MemoramaScreen.route}/2") }) {
            Text(text = "Nivel 2")
        }
        Button(onClick = { navController.navigate("${AppRoutes.MemoramaScreen.route}/3") }) {
            Text(text = "Nivel 3")
        }
    }
}