package com.example.proyectonuevoamanecer.screens.modalUI

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.proyectonuevoamanecer.api.GrupoAPI
import com.example.proyectonuevoamanecer.api.MiembroAPI
import com.example.proyectonuevoamanecer.api.UsuarioAPI
import com.example.proyectonuevoamanecer.databases.DbDatabase
import com.example.proyectonuevoamanecer.databases.Repositorio
import com.example.proyectonuevoamanecer.databases.UsuarioActivo
import com.example.proyectonuevoamanecer.databases.obtenerDatos
import com.example.proyectonuevoamanecer.screens.AppRoutes
import com.example.proyectonuevoamanecer.screens.Navegacion
import kotlinx.coroutines.launch
import org.json.JSONObject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalUi(navController: NavHostController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val isAtStartDestination = currentRoute == AppRoutes.HomeScreen.route
    val isAtLogin = currentRoute == AppRoutes.LoginScreen.route
    val appBarColor = when (currentRoute) {
        AppRoutes.Numeros.route -> MaterialTheme.colorScheme.tertiary
        AppRoutes.Rompecabezas.route -> Color.hsl(74F,0.23F,0.87F,1F)
        AppRoutes.EligirImagen.route -> Color.White
        else -> Color.Transparent
    }
    val altColor = if (appBarColor != Color.Transparent) Color.Black else Color.White
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.fillMaxWidth(0.65F)
            ) {
                ModalDrawerContent(
                    currentScreen = currentRoute,
                    navController = navController,
                    drawerState)}
            },
        ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Nuevo Amanecer") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = appBarColor,
                        titleContentColor = altColor),
                    navigationIcon = {
                        if (!isAtLogin) {
                            IconButton(onClick = {
                                if (!isAtStartDestination) { navController.popBackStack() }}
                            ) {
                                if (isAtStartDestination) {
                                    Icon(Icons.Filled.Home, contentDescription = "Home", tint = altColor)
                                } else {
                                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = altColor)
                                }
                            }
                        }
                    },
                    actions = {
                        if(!isAtLogin){
                            IconButton(onClick = {
                                scope.launch {
                                    drawerState.apply {
                                        if (isClosed) open() else close()
                                        }
                                    }
                            }) {
                                Icon(
                                    Icons.Filled.Menu,
                                    contentDescription = "Menu",
                                    tint = altColor
                                )
                            }
                        }
                    }
                )
            }, containerColor = Color.Transparent,

            ) { contentPadding ->
            Box(modifier = Modifier.padding(contentPadding)) {
                Navegacion(navController)
            }
        }
    }
}
@Composable
fun ModalDrawerContent(currentScreen: String?, navController: NavHostController, drawerState: DrawerState) {
    val context = LocalContext.current
    val db = DbDatabase.getInstance(context)
    val repositorio = Repositorio(db.dbDao())
    val scope = rememberCoroutineScope()
    var usuarioActivo by remember { mutableStateOf<UsuarioActivo?>(null) }
    var administrador by remember { mutableStateOf<UsuarioAPI?>(null) }
    var gruposMiembroUsuario by remember { mutableStateOf<MiembroAPI?>(null) }
    var grupoSeleccionado by remember { mutableStateOf<GrupoAPI?>(null) }
    var ids: Map<String, String?>?
    if(drawerState.targetValue == DrawerValue.Open){
        LaunchedEffect(key1 = true) {
            usuarioActivo = repositorio.getUsuarioActivo()
            administrador = obtenerDatos(context,"usuario",mapOf("id" to "${usuarioActivo?.id_usuario}"))?.getJSONObject(0)?.let { UsuarioAPI(it) }
            ids = mapOf("id_grupo" to "${usuarioActivo?.id_grupo}", "id_usuario" to "${usuarioActivo?.id_usuario}")
            gruposMiembroUsuario = obtenerDatos(context, "miembros",ids)?.getJSONObject(0)?.let { MiembroAPI(it) }
            ids =  mapOf("id" to "${gruposMiembroUsuario?.Id_Grupo}")
            grupoSeleccionado = obtenerDatos(context,"grupo",ids)?.getJSONObject(0)?.let { GrupoAPI(it) }
        }
    }
    Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.padding(16.dp)) {
        Icon(Icons.Filled.AccountCircle , contentDescription = "Usuario")
        Text(text = administrador?.Nombre ?: "Invitado")
    }
    NavigationDrawerItem(
        label = { Text(text = grupoSeleccionado?.Nombre.toString() ?: "Grupo No seleccionado") },
        selected = false,
        onClick = {
            scope.launch {
                repositorio.setUsuarioActivo(UsuarioActivo(1,"Invitado","Invitado"))
                drawerState.close() }},
        icon = {Icon(Icons.Filled.Create, contentDescription = "Inicio")}
    )
    Text("Menu", modifier = Modifier.padding(16.dp))
    Divider()
    NavigationDrawerItem(
        label = { Text(text = "Inicio") },
        selected = false,
        onClick = {
        navController.navigate(AppRoutes.HomeScreen.route) {
            popUpTo(AppRoutes.HomeScreen.route) { inclusive = true } }
            scope.launch { drawerState.close() }},
        icon = {Icon(Icons.Filled.Home, contentDescription = "Inicio")}
    )

    NavigationDrawerItem(
        label = { Text(text = "Juegos") },
        selected = false,
        onClick = {
        navController.navigate(AppRoutes.JuegosScreen.route) {
            popUpTo(AppRoutes.JuegosScreen.route) { inclusive = true } }
            scope.launch { drawerState.close() }},
        icon = {Icon(Icons.Filled.PlayArrow, contentDescription = "Inicio")}
        )
    if(administrador?.Admin == true){
        NavigationDrawerItem(
            label = { Text(text = "Administracion") },
            selected = false,
            onClick = {/* TODO */},
            icon = {Icon(Icons.Filled.AddCircle, contentDescription = "Inicio")}
        )
    }
    NavigationDrawerItem(
        label = { Text(text = "Cerrar Sesion") },
        selected = false,
        onClick = {
        // Aquí puedes agregar la lógica para cerrar la sesión
        navController.navigate(AppRoutes.LoginScreen.route) {
            popUpTo(AppRoutes.LoginScreen.route) { inclusive = true }
            scope.launch { drawerState.close() }}
    },
        icon = {Icon(Icons.Filled.ExitToApp, contentDescription = "Inicio")})
}