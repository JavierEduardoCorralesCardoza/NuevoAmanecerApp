package com.example.proyectonuevoamanecer.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.proyectonuevoamanecer.screens.AppRoutes

@Composable
fun LoginScreen(navController: NavController){
    val viewModel: LoginViewModel = viewModel()

    LoginBodyContent(navController, viewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginBodyContent(navController: NavController, viewModel: LoginViewModel){
    var texto by remember { mutableStateOf("") }

    Column {
        Text(text = "LogIn")
        TextField(
            value = texto,
            onValueChange = {texto = it},
            label = { Text(text = "Codigo")}
        )
        Button(onClick = {
            navController.navigate(AppRoutes.HomeScreen.route)
        }) {
            Text(text = "Iniciar Sesion")
        }
    }
}