package com.example.proyectonuevoamanecer.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.proyectonuevoamanecer.screens.AppRoutes
import com.example.proyectonuevoamanecer.screens.config.Configuracion
import com.example.proyectonuevoamanecer.screens.config.ConfiguracionViewModel

@Composable
fun HomeScreen(navController: NavController){
    BodyContent(navController)
}

@Composable
fun BodyContent(navController: NavController){
    val configViewModel:ConfiguracionViewModel = viewModel()

    Column {
        Text(text = "Luminaria")
        Button(onClick = { navController.navigate(route = AppRoutes.MainFlashMenu.route) }) {
            Text(text = "FlashCards")
        }
        Button(onClick = { navController.navigate(route = AppRoutes.JuegosScreen.route) }) {
            Text(text = "Minijuegos")
        }

        Button(onClick = { configViewModel.configuracionAbierta.value = true }) {
            Text(text = "Configuracion")
        }
        Button(onClick = {navController.navigate(AppRoutes.MainFlashMenu.route)}){
            Text(text="Tarjetas Educativas")
        }
    }

    Configuracion()
}