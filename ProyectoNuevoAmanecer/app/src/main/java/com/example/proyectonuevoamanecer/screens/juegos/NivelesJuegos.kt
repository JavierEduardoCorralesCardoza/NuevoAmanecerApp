package com.example.proyectonuevoamanecer.screens.juegos

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun NivelesJuegos(navController: NavController){
    BodyContentNiveles(navController)
}

@Composable
fun BodyContentNiveles(navController: NavController){
    Column {
        Text(text = "Niveles")
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Nivel 1")
        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Nivel 2")
        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Nivel 3")
        }
    }
}