package com.example.proyectonuevoamanecer.screens.juegos.memorama

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.proyectonuevoamanecer.R
import com.example.proyectonuevoamanecer.clases.CartasMemorama
import com.example.proyectonuevoamanecer.screens.AppRoutes

@Composable
fun MemoramaScreen(navController: NavController){
    val viewModel: MemoramaViewModel = viewModel()
    viewModel.ListaDeImagenes(3)
    viewModel.GenerarCartas(3)
    BodyContent(navController, viewModel)
}

@Composable
fun BodyContent(navController: NavController, viewModel: MemoramaViewModel){
    Column {
        Text(text = "Hola")
        Button(onClick = { navController.navigate(AppRoutes.HomeScreen.route) }) {
            Text(text = "Inicio")
        }
        ListOfCards(cartas = viewModel.cartas, cartas_volteadas = viewModel.cartas_volteadas)
    }
}

@Composable
fun ListOfCards(cartas: List<CartasMemorama>, cartas_volteadas: MutableMap<Int, Int>){

    LazyColumn{
        items(cartas){
            carta ->
            Card(modifier = Modifier.clickable {
                if (!carta.volteada){
                    carta.volteada = true
                    cartas_volteadas[carta.imagen] = cartas_volteadas[carta.imagen]?.plus(1) ?: 0
                    if (cartas.all { it.volteada }) {
                        println("Haz ganadoooooo")
                    }
                }
            }) {
                if(!carta.volteada) {
                    Image(
                        painter = painterResource(id = R.drawable.dorsal_carta),
                        contentDescription = "Dorsal de la carta",
                        modifier = Modifier.size(200.dp)
                    )
                }
                else {
                    Image(painter = painterResource(id = carta.imagen),
                        contentDescription = "Imagen carta",
                        modifier = Modifier.size(200.dp)
                    )
                }
            }
        }
    }
}