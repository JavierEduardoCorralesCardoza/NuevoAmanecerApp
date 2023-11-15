package com.example.proyectonuevoamanecer.screens.juegos

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.proyectonuevoamanecer.screens.AppRoutes

@Composable
fun JuegosScreen(navController: NavController){
    BodyContentJuegos(navController)
}

@Composable
fun BodyContentJuegos(navController: NavController){
    Column {
        Text(text = "Minijuegos")
        Button(onClick = { navController.navigate("${AppRoutes.NivelesJuegos.route}/${AppRoutes.MemoramaScreen.route}") }) {
            Text(text = "Memorama")
        }
        Button(onClick = { }) {
            Text(text = "Eleccion de imagen")
        }
        Button(onClick = {navController.navigate("${AppRoutes.NivelesJuegos.route}/${AppRoutes.DiferenciasCard.route}")}) {
            Text(text = "Encontrar diferencias")
        }
        Button(onClick = { }) {
            Text(text = "Secuencia de numeros")
        }
        Button(onClick = { }) {
            Text(text = "Rompecabezas")
        }
        Button(onClick = { navController.navigate(AppRoutes.HomeScreen.route)  }) {
            Text(text = "Regresar")
        }
    }
}