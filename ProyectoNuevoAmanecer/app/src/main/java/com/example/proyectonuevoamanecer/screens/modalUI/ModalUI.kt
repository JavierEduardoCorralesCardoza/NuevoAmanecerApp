package com.example.proyectonuevoamanecer.screens.modalUI

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.proyectonuevoamanecer.databases.DbDatabase
import com.example.proyectonuevoamanecer.databases.Repositorio
import com.example.proyectonuevoamanecer.databases.Usuario
import com.example.proyectonuevoamanecer.databases.UsuarioActivo
import com.example.proyectonuevoamanecer.screens.AppRoutes
import com.example.proyectonuevoamanecer.screens.Navegacion
import kotlinx.coroutines.launch

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
    val AltColor = if (appBarColor != Color.Transparent) Color.Black else Color.White
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet { ModalDrawerContent(currentScreen = currentRoute, navController = navController, drawerState)}
        },
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Nuevo Amanecer") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = appBarColor,
                        titleContentColor = AltColor),
                    navigationIcon = {
                        if (!isAtLogin) {
                            IconButton(onClick = {
                                if (!isAtStartDestination) { navController.popBackStack() }}
                            ) {
                                if (isAtStartDestination) {
                                    Icon(Icons.Filled.Home, contentDescription = "Home", tint = AltColor)
                                } else {
                                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = AltColor)
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
                                    tint = AltColor
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
    val db = DbDatabase.getInstance(LocalContext.current)
    val repositorio = Repositorio(db.dbDao())
    val scope = rememberCoroutineScope()
    val usuarioActivo = remember { mutableStateOf<UsuarioActivo?>(null) }
    val administrador = remember { mutableStateOf<Usuario?>(null) }
    if(drawerState.targetValue == DrawerValue.Open){
    LaunchedEffect(key1 = true) {
        usuarioActivo.value = repositorio.getUsuarioActivo()
        administrador.value = usuarioActivo.value?.let { repositorio.getUsuario(it.id_usuario) }
    } }
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
    if(administrador.value?.administrador == true){
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