package com.example.proyectonuevoamanecer.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.proyectonuevoamanecer.screens.AppRoutes

@Composable
fun HomeScreen(navController: NavController){
    BodyContent(navController)
}

@Composable
fun BodyContent(navController: NavController){
    Column {
        Text(text = "Hola")
        Button(onClick = { navController.navigate(route = AppRoutes.MemoramaScreen.route) }) {
            Text(text = "Memorama")
        }
        Button(onClick = {navController.navigate(AppRoutes.MainFlashMenu.route)}){

        }
    }
}