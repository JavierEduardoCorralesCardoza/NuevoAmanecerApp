package com.example.proyectonuevoamanecer.screens.juegos.numeros.configuracion

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.proyectonuevoamanecer.screens.juegos.numeros.viewModel.NumerosViewModel

@Composable
fun MenuConfiguracion(viewModel: NumerosViewModel) {
    // Categorias del menu
    var expandedNiveles by remember { mutableStateOf(false) }
    var expandedTiempo by remember { mutableStateOf(false) }
    var expandedPuntos by remember { mutableStateOf(false) }
    LazyColumn {
        // Niveles
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                TextButton(onClick = { if (!viewModel.juegoEnProgreso.value) expandedNiveles = !expandedNiveles }) {
                    Text(text = "Niveles",modifier = Modifier
                        .wrapContentSize(Alignment.Center))
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Niveles Dropdown")
                }
            }
            if (expandedNiveles) {
                viewModel.niveles.forEachIndexed { index, nivel ->
                    var expanded by remember { mutableStateOf(false) }
                    var rangoMax by remember { mutableStateOf(nivel.rango.last.toString()) }
                    var rangoMin by remember { mutableStateOf(nivel.rango.first.toString()) }
                    var cantidadNumeros by remember { mutableStateOf(nivel.cantidadNumeros.toString()) }
                    var orden by remember { mutableStateOf(nivel.orden.name) }

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            TextButton(onClick = { expanded = !expanded }) {
                                Text(text = "Nivel ${index + 1}")
                                Icon(Icons.Default.ArrowDropDown, contentDescription = "Nivel Dropdown")
                            }
                        }
                        if (expanded) {
                            Column {
                                Text(text = "Rango", modifier = Modifier.align(Alignment.Start).padding(8.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    OutlinedTextField(
                                        value = rangoMin,
                                        onValueChange = { rangoMin = it },
                                        modifier= Modifier.padding(8.dp).weight(1f),
                                        label = { Text("Min") },
                                        shape = MaterialTheme.shapes.small

                                    )
                                    OutlinedTextField(
                                        value = rangoMax,
                                        onValueChange = { rangoMax = it },
                                        modifier= Modifier.padding(8.dp).weight(1f),
                                        label = { Text("Max") },
                                        shape = MaterialTheme.shapes.small
                                    )
                                }
                                OutlinedTextField(
                                    value = cantidadNumeros,
                                    onValueChange = { cantidadNumeros = it },
                                    modifier= Modifier.padding(8.dp),
                                    label = { Text("Cantidad de números") },
                                    shape = MaterialTheme.shapes.small
                                )
                                OutlinedTextField(
                                    value = orden,
                                    onValueChange = { orden = it },
                                    modifier= Modifier.padding(8.dp),
                                    label = { Text("Orden") },
                                    shape = MaterialTheme.shapes.small
                                )
                                Button(onClick = {
                                    val nuevoNivel = Nivel(
                                        rango = rangoMin.toInt()..rangoMax.toInt(),
                                        cantidadNumeros = cantidadNumeros.toInt(),
                                        orden = Orden.valueOf(orden)
                                    )
                                    viewModel.niveles = viewModel.niveles.toMutableList().apply { set(index, nuevoNivel) }
                                }) {
                                    Text("Actualizar Nivel")
                                }
                            }
                        }

                    }
                }
            }
        }

        // Tiempo
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                TextButton(onClick = { expandedTiempo = !expandedTiempo }) {
                    Text(text = "Tiempo",modifier = Modifier
                        .wrapContentSize(Alignment.CenterStart))
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Tiempo Dropdown")
                }
            }
            if (expandedTiempo) {
                var tiempoInicial by remember { mutableStateOf(viewModel.tiempoInicial.toString()) }
                var tiempoAgregar by remember { mutableStateOf(viewModel.tiempoAgregar.toString()) }
                var tiempoClickIncorrecto by remember { mutableStateOf(viewModel.tiempoClickIncorrecto.toString()) }

                Column {
                    OutlinedTextField(
                        value = tiempoInicial,
                        onValueChange = { tiempoInicial = it },
                        modifier= Modifier.padding(8.dp),
                        label = { Text("Tiempo Inicial",modifier = Modifier
                            .wrapContentSize(Alignment.CenterStart)) },
                        shape = MaterialTheme.shapes.small
                    )
                    OutlinedTextField(
                        value = tiempoAgregar,
                        onValueChange = { tiempoAgregar = it },
                        modifier= Modifier.padding(8.dp),
                        label = { Text("Tiempo Agregar",modifier = Modifier
                            .wrapContentSize(Alignment.CenterStart)) },
                        shape = MaterialTheme.shapes.small
                    )
                    OutlinedTextField(
                        value = tiempoClickIncorrecto,
                        onValueChange = { tiempoClickIncorrecto = it },
                        modifier= Modifier.padding(8.dp),
                        label = { Text("Tiempo Click Incorrecto",modifier = Modifier
                            .wrapContentSize(Alignment.CenterStart)) },
                        shape = MaterialTheme.shapes.small
                    )
                    Button(onClick = {
                        viewModel.tiempoInicial = tiempoInicial.toLong()
                        viewModel.tiempoAgregar = tiempoAgregar.toLong()
                        viewModel.tiempoClickIncorrecto = tiempoClickIncorrecto.toLong()
                    }) {
                        Text("Actualizar Tiempo",modifier = Modifier
                            .wrapContentSize(Alignment.CenterStart))
                    }
                }
            }

        }

        // Puntos
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                TextButton(onClick = { expandedPuntos = !expandedPuntos }) {
                    Text(text = "Puntos",modifier = Modifier
                        .wrapContentSize(Alignment.CenterStart))
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Puntos Dropdown")
                }
            }
            if (expandedPuntos) {
                var puntosClickCorrecto by remember { mutableStateOf(viewModel.puntosClickCorrecto.toString()) }
                var puntosClickIncorrecto by remember { mutableStateOf(viewModel.puntosClickInCorrecto.toString()) }
                var puntosCompletarSet by remember { mutableStateOf(viewModel.puntosCompletarSet.toString()) }

                Column {
                    OutlinedTextField(
                        value = puntosClickCorrecto,
                        onValueChange = { puntosClickCorrecto = it },
                        modifier= Modifier.padding(8.dp),
                        label = { Text("Puntos Click Correcto",modifier = Modifier
                            .wrapContentSize(Alignment.CenterStart)) },
                        shape = MaterialTheme.shapes.small
                    )
                    OutlinedTextField(
                        value = puntosClickIncorrecto,
                        onValueChange = { puntosClickIncorrecto = it },
                        modifier= Modifier.padding(8.dp),
                        label = { Text("Puntos Click Incorrecto",modifier = Modifier
                            .wrapContentSize(Alignment.CenterStart)) },
                        shape = MaterialTheme.shapes.small
                    )
                    OutlinedTextField(
                        value = puntosCompletarSet,
                        onValueChange = { puntosCompletarSet = it },
                        modifier= Modifier.padding(8.dp),
                        label = { Text("Puntos Completar Set",modifier = Modifier
                            .wrapContentSize(Alignment.CenterStart)) },
                        shape = MaterialTheme.shapes.small
                    )
                    Button(onClick = {
                        viewModel.puntosClickCorrecto = puntosClickCorrecto.toInt()
                        viewModel.puntosClickInCorrecto = puntosClickIncorrecto.toInt()
                        viewModel.puntosCompletarSet = puntosCompletarSet.toInt()
                    }) {
                        Text("Actualizar Puntos",modifier = Modifier
                            .wrapContentSize(Alignment.CenterStart))
                    }
                }
            }

        }
    }
}