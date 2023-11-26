package com.example.proyectonuevoamanecer.clases

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog


@Composable
fun BotonBase(contenido: @Composable () -> Unit) {
    var showDialog by remember { mutableStateOf(false) }

    IconButton(onClick = { showDialog = true }) {
        Icon(Icons.Default.Settings, contentDescription = "Configuración")
    }

    if (showDialog) {
        Dialog(onDismissRequest = { showDialog = false }) {
            abrirMenuConfiguracion(contenido = contenido, onDismiss = { showDialog = false })
        }
    }
}

@Composable
fun abrirMenuConfiguracion(contenido: @Composable () -> Unit, onDismiss: () -> Unit) {
    OutlinedCard(
        modifier = Modifier.padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Column {
            IconButton(onClick = onDismiss) {
                Icon(Icons.Default.Close, contentDescription = "Cerrar")
            }
            // Elementos comunes del menú de configuración
            Text(text = "Configuración del juego",modifier = Modifier
                .wrapContentSize(Alignment.Center)
                .padding(8.dp))
            MenuConfiguracion(contenido)
        }
    }
}
@Composable
fun MenuConfiguracion(contenido: @Composable () -> Unit) {
        // Aquí se renderizará el contenido específico de cada juego
        contenido()
}




