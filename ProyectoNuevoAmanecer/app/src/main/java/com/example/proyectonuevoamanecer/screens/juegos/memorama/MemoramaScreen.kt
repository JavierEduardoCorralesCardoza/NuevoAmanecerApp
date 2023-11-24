package com.example.proyectonuevoamanecer.screens.juegos.memorama

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.proyectonuevoamanecer.R
import com.example.proyectonuevoamanecer.clases.CartasMemorama
import com.example.proyectonuevoamanecer.screens.AppRoutes
import kotlin.math.roundToInt
import kotlin.math.sqrt

@Composable
fun MemoramaScreen(navController: NavController, nivel: Int){
    val viewModel: MemoramaViewModel = viewModel()

    val numCartas: Int = when (nivel){
        1 -> 3
        2 -> 6
        3 -> 10
        else -> {
            0
        }
    }
    viewModel.ListaDeImagenes(numCartas)
    viewModel.GenerarCartas(numCartas)
    BodyContent(navController, viewModel)
}

@Composable
fun BodyContent(navController: NavController, viewModel: MemoramaViewModel){
    Column {
        Text(text = viewModel.score.value.toString())
        ListOfCards(cartas = viewModel.cartas, indexCartasVolteadas = viewModel.indexCartasVolteadas, viewModel = viewModel)
        WinningMessage(viewModel = viewModel, navController)
    }
}

@Composable
fun NavigationButton(navController: NavController) {
    Button(onClick = { navController.navigate(AppRoutes.HomeScreen.route) }) {
        Text(text = "Inicio")
    }
}

@Composable
fun WinningMessage(viewModel: MemoramaViewModel, navController: NavController){
    if (viewModel.cuadroDeDialogo.value) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("¡Felicidades!") },
            text = { Text("Haz ganadoooooo. Score: "+viewModel.score.value.toString()) },
            confirmButton = {
                NavigationButton(navController = navController)
            }
        )
    }
}

@Composable
fun ListOfCards(cartas: List<CartasMemorama>, indexCartasVolteadas: MutableList<Int>, viewModel: MemoramaViewModel){
    val numberOfCardsPerRow = sqrt(cartas.size.toDouble()).roundToInt()
    val chunkedCartas = cartas.chunked(numberOfCardsPerRow)
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(chunkedCartas){ rowOfCartas ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (carta in rowOfCartas) {
                    CardItem(carta, cartas, indexCartasVolteadas, viewModel)
                }
            }
        }
    }
}

@Composable
fun CardItem(carta: CartasMemorama, cartas: List<CartasMemorama>, indexCartasVolteadas: MutableList<Int>, viewModel: MemoramaViewModel) {
    val configuration = LocalConfiguration.current
    val cardSize = configuration.screenWidthDp.dp / sqrt(cartas.size.toDouble()).roundToInt()

    if(!carta.volteada) {
        Image(
            painter = painterResource(id = R.drawable.dorsal_carta),
            contentDescription = "Dorsal de la carta",
            modifier = Modifier
                .size(cardSize)
                .clickable(enabled = !viewModel.retrasoEnEjecucion.value) {
                    onCardClick(carta, cartas, indexCartasVolteadas, viewModel)
                }
        )
    }
    else {
        Image(
            painter = painterResource(id = carta.imagen),
            contentDescription = "Imagen carta",
            modifier = Modifier
                .size(cardSize)
                .clickable(enabled = !viewModel.retrasoEnEjecucion.value) {
                    onCardClick(carta, cartas, indexCartasVolteadas, viewModel)
                }
        )
    }
}

fun onCardClick(carta: CartasMemorama, cartas: List<CartasMemorama>, indexCartasVolteadas: MutableList<Int>, viewModel: MemoramaViewModel) {
    if (!carta.volteada && indexCartasVolteadas.size < 2) {
        carta.volteada = true
        indexCartasVolteadas.add(cartas.indexOf(carta))

        if (cartas.all { it.volteada }) {
            viewModel.retrasoEnEjecucion.value = true
            viewModel.cuadroDeDialogo.value = true
        }

        if (indexCartasVolteadas.size == 2) {
            if (cartas[indexCartasVolteadas[0]].imagen == cartas[indexCartasVolteadas[1]].imagen) {
                indexCartasVolteadas.clear()
                viewModel.score.value = viewModel.score.value+100
            } else {
                viewModel.voltearCartasConRetraso(cartas, indexCartasVolteadas)
                viewModel.score.value = viewModel.score.value-10
            }
        }
    } else if (indexCartasVolteadas.size == 2) {
        if (cartas[indexCartasVolteadas[0]].imagen != cartas[indexCartasVolteadas[1]].imagen) {
            viewModel.voltearCartasConRetraso(cartas, indexCartasVolteadas)
            viewModel.score.value = viewModel.score.value-10
        } else {
            indexCartasVolteadas.clear()
            viewModel.score.value = viewModel.score.value+100
        }
    }
}
