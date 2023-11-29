package com.example.proyectonuevoamanecer.screens.juegos

import android.graphics.fonts.FontStyle
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun NivelesJuegos(navController: NavController, ruta: String){
    BodyContentNiveles(navController, ruta)
}

@Composable
fun BodyContentNiveles(navController: NavController, ruta: String){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Selecciona un Nivel",
            style = typography.headlineMedium,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { navController.navigate("${ruta}/1") }) {
            Text(text = "Nivel 1")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = { navController.navigate("${ruta}/2") }) {
            Text(text = "Nivel 2")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = { navController.navigate("${ruta}/3") }) {
            Text(text = "Nivel 3")
        }
    }
}