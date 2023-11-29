package com.example.proyectonuevoamanecer.screens.juegos.memorama

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    var colorTexto = Color.White
    var cartas = viewModel.allImages.collectAsState(initial = emptyList())

    var sliderPosition by remember { mutableFloatStateOf(0f) }
    val intervalStart = 0f
    var intervalEnd by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(cartas.value) {
        intervalEnd = cartas.value.size.toFloat()
    }

    val showAddCardDialog = remember{ mutableStateOf(false)}
    val showDeleteCardDialog = remember{ mutableStateOf(false)}
    val showDialogVacio = remember{ mutableStateOf(false)}

    Column(
        modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.4f)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Menu",
            color = colorTexto,
            modifier = Modifier.fillMaxWidth().padding(40.dp),
            textAlign = TextAlign.Center,
            fontSize = 40.sp
        )
        Text(
            text = "Elige el numero de cartas: ${sliderPosition.toInt()}",
            color = colorTexto,
            fontSize = 20.sp
        )
        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            valueRange = intervalStart..intervalEnd,
            modifier = Modifier.width(300.dp).padding(10.dp)
        )

        Button(
            onClick = { showAddCardDialog.value = true },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(containerColor = Color(130, 148, 196))
        ) {
            Text(
                text = "Agregar Carta",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
        }

        Button(
            onClick = { showDeleteCardDialog.value = true },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(containerColor = Color(172, 177, 214))
        ) {
            Text(
                text = "Eliminar carta",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
        }

        Button(
            onClick = {
                if(sliderPosition.toInt() != 0) {
                    navController.navigate("${AppRoutes.MemoramaScreen.route}/${sliderPosition.toInt()}")
                }
                else{
                    showDialogVacio.value = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(containerColor = Color(219, 223, 234))
        ) {
            Text(
                text = "Jugar",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
        }
    }

    if(showAddCardDialog.value){
        AddCardDialog(showDialog = showAddCardDialog){carta ->
            viewModel.insertCarta(carta)
            sliderPosition = intervalStart
        }
    }
    if(showDeleteCardDialog.value){
        DeleteCardDialog(showDialog = showDeleteCardDialog, cartas = cartas.value){carta ->
            viewModel.deleteCarta(carta)
            sliderPosition = intervalStart
        }
    }
    if(showDialogVacio.value){
        AlertDialog(
            onDismissRequest = {showDialogVacio.value = false},
            title={Text("Error")},
            text={Text("No se estan seleccionando cartas. Ingrese un numero.")},
            confirmButton = {
                Button(
                    onClick={showDialogVacio.value = false}
                ){
                    Text("Aceptar")
                }
            }
        )
    }
}