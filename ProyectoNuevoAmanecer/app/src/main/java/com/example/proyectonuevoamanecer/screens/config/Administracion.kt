package com.example.proyectonuevoamanecer.screens.config

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

@Composable
fun Administracion(navController: NavController){
    AdministracionTabs()
}
@Composable
fun AdministracionTabs(){
    var tabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Perfiles", "Usuarios","Grupos", "Juegos")
    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = tabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(text = { Text(title) },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index },
                    icon = {
                        when (index) {
                            0 -> Icon(imageVector = Icons.Default.AccountBox, contentDescription = "Perfiles")
                            1 -> Icon(imageVector = Icons.Default.Face, contentDescription = "Usuarios")
                            2 -> Icon(imageVector = Icons.Filled.Info, contentDescription = "Grupos")
                            3 -> Icon(imageVector = Icons.Filled.Settings, contentDescription = "Juegos")
                        }
                    }
                )
            }
        }
        when (tabIndex) {
            0 -> Perfiles()
            1 -> Usuarios()
            2 -> Grupos()
            3 -> Juegos()
        }
        Divider()
    }
}


