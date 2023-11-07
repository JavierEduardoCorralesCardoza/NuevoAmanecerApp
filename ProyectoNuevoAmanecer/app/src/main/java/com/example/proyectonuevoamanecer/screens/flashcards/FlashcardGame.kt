package com.example.proyectonuevoamanecer.screens.flashcards

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyectonuevoamanecer.R
import com.example.proyectonuevoamanecer.ui.theme.ProyectoNuevoAmanecerTheme

@Composable
fun FlashcardGame(navController: NavController){
    BodyGameContent(navController)
}

@Composable
fun BodyGameContent(navController: NavController){
    val (selectedAnswer, onAnswerSelected) = remember { mutableStateOf("") }
    val correctAnswer = "Respuesta correcta"

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(id = R.drawable.imagen_memorama1), contentDescription = "Flashcard Image")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { onAnswerSelected("Opción 1") }) {
            Text(text = "Opción 1")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { onAnswerSelected("Opción 2") }) {
            Text(text = "Opción 2")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (selectedAnswer.isNotEmpty()) {
            Text(text = if (selectedAnswer == correctAnswer) "¡Correcto!" else "Incorrecto, la respuesta correcta es $correctAnswer")
        }
    }
}

