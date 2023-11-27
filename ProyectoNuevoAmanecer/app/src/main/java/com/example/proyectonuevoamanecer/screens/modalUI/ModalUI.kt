package com.example.proyectonuevoamanecer.screens.modalUI

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
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

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet { /* Drawer content */ }
        },
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Nuevo Amanecer") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = Color.White),
                    navigationIcon = {
                        if (!isAtLogin) {
                            IconButton(onClick = {
                                if (isAtStartDestination) {
                                    navController.navigate(AppRoutes.HomeScreen.route) {
                                        popUpTo(AppRoutes.HomeScreen.route) { inclusive = true }
                                    }
                                } else {
                                    navController.popBackStack()
                                }
                            }) {
                                if (isAtStartDestination) {
                                    Icon(Icons.Filled.Home, contentDescription = "Home", tint = Color.White)
                                } else {
                                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                                }
                            }
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                    }
                                }
                        }) {
                            Icon(Icons.Filled.Menu,contentDescription = "Menu", tint = Color.White)
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


