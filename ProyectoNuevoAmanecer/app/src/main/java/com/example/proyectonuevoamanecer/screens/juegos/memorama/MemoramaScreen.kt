package com.example.proyectonuevoamanecer.screens.juegos.memorama

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyectonuevoamanecer.R
import com.example.proyectonuevoamanecer.clases.CartasMemorama
import com.example.proyectonuevoamanecer.screens.AppRoutes

@Composable
fun MemoramaScreen(navController: NavController){
    BodyContent(navController)
}

@Composable
fun BodyContent(navController: NavController){

    val cartas = listOf<CartasMemorama>(
        CartasMemorama(imagen = R.drawable.frente_carta, volteada = false),
        CartasMemorama(imagen = R.drawable.frente_carta, volteada = false))

    val cartas_volteadas = mutableMapOf<Int, Int>(R.drawable.frente_carta to 0, R.drawable.as_de_picas to 0)

    Column {
        Text(text = "Hola")
        Button(onClick = { navController.navigate(AppRoutes.HomeScreen.route) }) {
            Text(text = "Inicio")
        }
        ListOfCards(cartas = cartas, cartas_volteadas = cartas_volteadas)
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