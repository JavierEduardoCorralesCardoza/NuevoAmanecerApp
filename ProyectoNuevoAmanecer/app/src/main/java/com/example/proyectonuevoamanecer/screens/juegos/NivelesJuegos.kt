package com.example.proyectonuevoamanecer.screens.juegos

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun NivelesJuegos(navController: NavController, ruta: String){
    BodyContentNiveles(navController, ruta)
}

@Composable
fun BodyContentNiveles(navController: NavController, ruta: String){
    Column {
        Text(text = "Niveles")
        Button(onClick = { navController.navigate("${ruta}/1") }) {
            Text(text = "Nivel 1")
        }
        Button(onClick = { navController.navigate("${ruta}/2") }) {
            Text(text = "Nivel 2")
        }
        Button(onClick = { navController.navigate("${ruta}/3") }) {
            Text(text = "Nivel 3")
        }
    }
}