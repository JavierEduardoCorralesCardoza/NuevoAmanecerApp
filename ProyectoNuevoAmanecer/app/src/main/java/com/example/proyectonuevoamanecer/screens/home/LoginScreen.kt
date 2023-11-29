package com.example.proyectonuevoamanecer.screens.home

import android.content.Context
import android.view.WindowManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.proyectonuevoamanecer.R
import com.example.proyectonuevoamanecer.api.MiembroAPI
import com.example.proyectonuevoamanecer.api.UsuarioAPI
import com.example.proyectonuevoamanecer.api.Variables
import com.example.proyectonuevoamanecer.api.llamarApi
import com.example.proyectonuevoamanecer.databases.DbDatabase
import com.example.proyectonuevoamanecer.databases.Miembro
import com.example.proyectonuevoamanecer.databases.Repositorio
import com.example.proyectonuevoamanecer.databases.Usuario
import com.example.proyectonuevoamanecer.databases.UsuarioActivo
import com.example.proyectonuevoamanecer.screens.AppRoutes
import com.example.proyectonuevoamanecer.screens.config.findWindow
import com.example.proyectonuevoamanecer.widgets.Gif
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray

@Composable
fun LoginScreen(navController: NavController){
    val viewModel: LoginViewModel = viewModel()
    val layoutParams = LocalContext.current.findWindow().attributes
    layoutParams.dimAmount = 0.5f
    LocalContext.current.findWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    LocalContext.current.findWindow().attributes = layoutParams
    LoginBodyContent(navController, viewModel)
}

@Composable
fun LoginBodyContent(navController: NavController, viewModel: LoginViewModel) {
    val context = LocalContext.current
    var clave by remember { mutableStateOf("") }
    var data = emptyMap<String, Any>()
    var pase by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    val db = DbDatabase.getInstance(context)
    val sharedPreferences = context.getSharedPreferences("preferencias", Context.MODE_PRIVATE)
    var mantenerSesion by remember {
        mutableStateOf(
            sharedPreferences.getBoolean(
                "mantenerSesion",
                false
            )
        )
    }
    val repositorio = Repositorio(db.dbDao())
    var usuarioActivo by remember { mutableStateOf<UsuarioActivo?>(null) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val isAtStartDestination = currentRoute == AppRoutes.LoginScreen.route

    LaunchedEffect(isAtStartDestination) {
        usuarioActivo = repositorio.getUsuarioActivo()
        if (usuarioActivo != null && !mantenerSesion) {
            repositorio.deleteUsuarioActivo(usuarioActivo!!)
            usuarioActivo = null
        } else if (usuarioActivo != null && mantenerSesion) {
            navController.navigate(AppRoutes.HomeScreen.route)
        }
    }

    if (usuarioActivo == null) {
        val yOffset = with(LocalDensity.current) { (-500).toDp() }
        Gif(
            R.drawable.star,
            modifier = Modifier
                .fillMaxSize()
                .absoluteOffset(y = yOffset)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "LogIn", modifier = Modifier.padding(top = 0.dp, bottom = 8.dp),
                style = MaterialTheme.typography.displayLarge,
                color = Color.White
            )
            TextField(
                value = clave,
                onValueChange = { clave = it },
                label = { Text(text = "Clave") },
                modifier = Modifier.padding(8.dp)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Mantener Sesión?", color = Color.White)
                Checkbox(
                    checked = mantenerSesion,
                    colors = CheckboxDefaults.colors(uncheckedColor = Color.White),
                    onCheckedChange = { checked ->
                        mantenerSesion = checked
                        with(sharedPreferences.edit()) {
                            putBoolean("mantenerSesion", checked)
                            apply()
                        }
                    },
                )
            }
            val scope = rememberCoroutineScope()
            var isConnected = Variables.isNetworkConnected
            Button(
                onClick = {
                    if (!isConnected) {
                        showDialog = true
                        return@Button
                    }
                    var response = llamarApi("usuario", data, "GET", mapOf("id" to clave))
                    var usuarioAPI: UsuarioAPI? = null
                    val usuariosArray = response.getJSONArray("Usuarios")
                    var usuario = Usuario("Invitado", "Invitado", false)
                    if (usuariosArray.length() > 0) {
                        usuarioAPI = UsuarioAPI(usuariosArray.getJSONObject(0))
                        usuario = Usuario(usuarioAPI.Id, usuarioAPI.Nombre, usuarioAPI.Admin)
                    } else {
                        showDialog = true
                        println("La respuesta no tiene el estado 'success'")
                    }
                    var miembrosArray = JSONArray()
                    if (usuarioAPI != null) {
                        response =
                            llamarApi("miembros", data, "GET", mapOf("id_usuario" to usuarioAPI.Id))
                        miembrosArray = response.getJSONArray("Miembros")
                    }
                    var miembroAPI: MiembroAPI?
                    var miembro = Miembro("Invitados", "Invitados", false)
                    if (miembrosArray.length() > 0) {
                        miembroAPI = MiembroAPI(miembrosArray.getJSONObject(0))
                        miembro = Miembro(
                            miembroAPI.Id_Grupo,
                            miembroAPI.Id_Usuario,
                            miembroAPI.Configuracion
                        )
                    }
                    if (usuario.id == clave) {
                        pase = true
                        scope.launch {
                            val existingUsuario = repositorio.getUsuario(mapOf("id" to usuario.id))
                            if (existingUsuario == null) {
                                repositorio.insertUsuario(usuario)
                            } else {
                                repositorio.updateUsuario(usuario)
                            }
                            val usuarioActivo = UsuarioActivo(1, usuario.id, miembro.id_grupo.toString())
                            println(repositorio.getUsuarioActivo())
                            withContext(Dispatchers.IO){repositorio.setUsuarioActivo(usuarioActivo)}
                            println(repositorio.getUsuarioActivo())

                        }
                    }
                    if (pase) {
                        navController.navigate(AppRoutes.HomeScreen.route)
                    }
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "Iniciar Sesión")
            }
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = {},
                    title = {
                        Text(
                            style = MaterialTheme.typography.displayMedium,
                            text = "Error al iniciar Sesión:"
                        )
                    },
                    text = {
                        Text(
                            style = MaterialTheme.typography.displaySmall,
                            text = if (isConnected) "¡Clave inválida!" else "Sin Conexión!\nIngrese como Invitado"
                        )
                    },
                    confirmButton = {
                        Button(onClick = { showDialog = false }) {
                            Text(
                                style = MaterialTheme.typography.headlineSmall,
                                text = "Intentar otra vez"
                            )
                        }
                    })
            }
            /*Button(onClick = {
                scope.launch {
                    repositorio.setUsuarioActivo(
                        UsuarioActivo(
                            1,
                            "Invitado",
                            "Invitado"
                        )
                    )
                }
                navController.navigate(AppRoutes.HomeScreen.route)
            }) {
                Text(text = "Iniciar Sesion RAPIDO")
            }*/
        }

    }
}


