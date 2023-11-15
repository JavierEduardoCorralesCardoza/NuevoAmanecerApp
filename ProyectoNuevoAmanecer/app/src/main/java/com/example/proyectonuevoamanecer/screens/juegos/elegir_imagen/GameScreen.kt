package com.example.juego1jetpc.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyectonuevoamanecer.R

@Composable
fun EligirImagen(navController: NavController) {
    val images = listOf(
        R.drawable.cat,
        R.drawable.banana,
        R.drawable.blueberries
    )
    val imageLabels = listOf("Gato", "Banana", "Mora")
    val correctAnswerIndex = 0 // índice de la imagen correcta

    var selectedAnswer by remember { mutableStateOf(-1) }
    var isCorrect by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Selecciona la imagen correcta",
            fontSize = 20.sp, // Aumenta el tamaño del texto de la instrucción
            fontWeight = FontWeight.Bold, // Pone el texto en negrita
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp) // Agrega espacio debajo del texto de la instrucción
        )
        Text(
            text = "Gato", // La palabra que va justo debajo de la instrucción
            fontSize = 18.sp, // Tamaño del texto para "Gato"
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp) // Agrega espacio debajo de la palabra "Gato"
        )

        images.forEachIndexed { index, imageRes ->
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = imageLabels[index], // Usa la etiqueta correspondiente para cada imagen
                modifier = Modifier
                    .size(150.dp) // Aumenta el tamaño de las imágenes
                    .padding(8.dp) // Añade relleno alrededor de las imágenes
                    .clickable {
                        selectedAnswer = index
                        isCorrect = index == correctAnswerIndex
                    }
            )
        }

        if (selectedAnswer >= 0) {
            Text(
                text = if (isCorrect) "¡Correcto!" else "Inténtalo de nuevo",
                fontSize = 18.sp, // Aumenta el tamaño del texto de retroalimentación
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp) // Añade espacio encima del texto de retroalimentación
            )
        }
    }
}
