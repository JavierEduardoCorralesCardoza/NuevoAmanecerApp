package com.example.proyectonuevoamanecer.screens.juegos.memorama

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.proyectonuevoamanecer.screens.AppRoutes
import com.example.proyectonuevoamanecer.screens.flashcards.FlashViewModelFactory
import com.example.proyectonuevoamanecer.screens.juegos.memorama.database.DBViewModel
import com.example.proyectonuevoamanecer.screens.juegos.memorama.database.DBViewModelFactory
import com.example.proyectonuevoamanecer.screens.juegos.memorama.database.DeleteCardDialog
import com.example.proyectonuevoamanecer.screens.juegos.memorama.database.MemoramaDatabase

@Composable
fun MemoramaMenuScreen(navController: NavController){
    val context = LocalContext.current
    val database = MemoramaDatabase.getInstance(context)
    val viewModel: DBViewModel = viewModel(factory = DBViewModelFactory(database))

    BodyContent(navController = navController, viewModel)
}

@Composable
fun BodyContent(navController: NavController, viewModel: DBViewModel){

    var cartas = viewModel.allImages.collectAsState(initial = emptyList())

    var sliderPosition by remember { mutableFloatStateOf(0f) }
    val intervalStart = 0f
    var intervalEnd by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(cartas.value) {
        intervalEnd = cartas.value.size.toFloat()
    }

    val showAddCardDialog = remember{ mutableStateOf(false)}
    val showDeleteCardDialog = remember{ mutableStateOf(false)}

    Column {
        Text(text = "Elige el numero de cartas")
        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            valueRange = intervalStart..intervalEnd,
            modifier = Modifier.width(200.dp)
        )

        Text(text = "El valor seleccionado es: ${sliderPosition.toInt()}")

        Button(onClick = {
            showAddCardDialog.value = true
        }) {
            Text(text = "Agregar Carta")
        }

        Button(onClick = {
            showDeleteCardDialog.value = true
        }) {
            Text(text = "Eliminar carta")
        }

        Button(onClick = { navController.navigate("${AppRoutes.MemoramaScreen.route}/${sliderPosition}") }) {
            Text(text = "Jugar")
        }
    }

    if(showAddCardDialog.value){
        AddCardDialog(showDialog = showAddCardDialog){carta ->
            viewModel.insertCarta(carta)
        }
    }
    if(showDeleteCardDialog.value){
        DeleteCardDialog(showDialog = showDeleteCardDialog, cartas = cartas.value){carta ->
            viewModel.deleteCarta(carta)
        }
    }
}