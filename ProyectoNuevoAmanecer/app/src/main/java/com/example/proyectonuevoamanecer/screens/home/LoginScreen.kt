package com.example.proyectonuevoamanecer.screens.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.proyectonuevoamanecer.databases.DbDatabase
import com.example.proyectonuevoamanecer.databases.Repositorio
import com.example.proyectonuevoamanecer.databases.Usuario
import com.example.proyectonuevoamanecer.databases.UsuarioActivo
import com.example.proyectonuevoamanecer.screens.AppRoutes
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavController){
    val viewModel: LoginViewModel = viewModel()

    LoginBodyContent(navController, viewModel)
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun LoginBodyContent(navController: NavController, viewModel: LoginViewModel) {
    val context = LocalContext.current
    var clave by remember { mutableStateOf("") }
    var data = emptyMap<String, Any>()
    var pase by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    val db = DbDatabase.getInstance(context)
    val sharedPreferences = context.getSharedPreferences("preferencias", Context.MODE_PRIVATE)
    var mantenerSesion by remember { mutableStateOf(sharedPreferences.getBoolean("mantenerSesion", false)) }
    val repositorio = Repositorio(db.dbDao())
    var usuarioActivo by remember { mutableStateOf<UsuarioActivo?>(null) }

    LaunchedEffect(key1 = true) {
        usuarioActivo = repositorio.getUsuarioActivo()
        if (usuarioActivo != null && mantenerSesion) {
            navController.navigate(AppRoutes.HomeScreen.route)
        }
    }

    if (usuarioActivo == null){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
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
            val scope = rememberCoroutineScope()
            Button(
                onClick = {
                    val response = llamarApi("usuario", data, "GET",mapOf("id" to clave))
                    val usuariosArray = response.getJSONArray("Usuarios")
                    if (usuariosArray.length() > 0) {
                        val usuario = usuariosArray.getJSONObject(0)
                        val id = usuario.getString("ID")
                        val nombre = usuario.getString("Nombre")
                        val administrador = usuario.getInt("Administrador") == 1
                        val objeto = Usuario(id, nombre, administrador)
                        if (id == clave)
                            pase = true
                        scope.launch {
                            val existingUsuario = repositorio.getUsuario(id)
                            if (existingUsuario == null) {
                                repositorio.insertUsuario(objeto)
                            } else {
                                repositorio.updateUsuario(objeto)
                            }
                            repositorio.setUsuarioActivo(UsuarioActivo(1,id))
                        }
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
            Checkbox(
                checked = mantenerSesion,
                onCheckedChange = { checked ->
                    mantenerSesion = checked
                    with (sharedPreferences.edit()) {
                        putBoolean("mantenerSesion", checked)
                        apply()
                    }
                },
            )
            Text(text = "Mantener sesión")
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
