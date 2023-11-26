package com.example.proyectonuevoamanecer.screens.home

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.example.proyectonuevoamanecer.R
import com.example.proyectonuevoamanecer.api.llamarApi
import com.example.proyectonuevoamanecer.screens.AppRoutes

@Composable
fun LoginScreen(navController: NavController){
    val viewModel: LoginViewModel = viewModel()

    LoginBodyContent(navController, viewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginBodyContent(navController: NavController, viewModel: LoginViewModel) {
    var clave by remember { mutableStateOf("") }
    var data = emptyMap<String, Any>()
    var pase by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Ajusta según sea necesario
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "LogIn", modifier = Modifier.padding(8.dp))
        TextField(
            value = clave,
            onValueChange = { clave = it },
            label = { Text(text = "Clave") },
            modifier = Modifier.padding(8.dp)
        )
        Button(
            onClick = {
                val response = llamarApi("usuario", data, "GET",mapOf("id" to clave))
                val usuariosArray = response.getJSONArray("Usuarios")
                if (usuariosArray.length() > 0) {
                    val usuario = usuariosArray.getJSONObject(0)
                    val id = usuario.getString("ID")
                    if (id == clave)
                        pase = true
                } else {
                    showDialog = true
                    println("La respuesta no tiene el estado 'success'")
                }
                if (pase) {
                    navController.navigate(AppRoutes.HomeScreen.route)
                }
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Iniciar Sesion")
        }
        if(showDialog){AlertDialog(
            onDismissRequest = {},
            title = { Text(style = MaterialTheme.typography.displayMedium, text = "Error de Inicio de Sesion:") },
            text = { Text(style = MaterialTheme.typography.displaySmall, text = "¡Clave invalida!") },
            confirmButton = {
                Button(onClick = {showDialog = false}) {
                    Text(style = MaterialTheme.typography.headlineSmall, text = "Intentar otra vez")
                }
            })}
        Button(onClick = { navController.navigate(AppRoutes.HomeScreen.route) }) {
            Text(text = "Iniciar Sesion RAPIDO")
        }
        Gif()
    }
}

@Composable
fun Gif() {
    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            if (SDK_INT >= 34) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    Image(
        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current)
                .data(data = R.drawable.star)
                .apply(block = fun ImageRequest.Builder.() {
                    //size(Size.ORIGINAL)
                }).build(),
            imageLoader = imageLoader
        ),
        contentDescription = null,
        modifier = Modifier
            .width(250.dp)
    )
}
