

package com.example.proyectonuevoamanecer.screens.flashcards

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.proyectonuevoamanecer.R
import com.example.proyectonuevoamanecer.screens.AppRoutes
import com.example.proyectonuevoamanecer.ui.theme.ProyectoNuevoAmanecerTheme
@Preview(showBackground = true)
@Composable
fun PreviewFlashcardGame() {
    val navController = rememberNavController()
    FlashcardGame(navController)
}

@Composable
fun FlashcardGame(navController: NavController){
    BodyGameContent(navController)
}

@Composable
fun BodyGameContent(navController: NavController) {
    Text(text="Mazo X",
        style = TextStyle(fontSize=24.sp,fontWeight = FontWeight.Bold),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        textAlign= TextAlign.Center)
    val (selectedAnswer, onAnswerSelected) = remember { mutableStateOf("") }
    val correctAnswer = "Respuesta correcta"
    val (isFlipped, setFlipped)=remember { mutableStateOf(false)}

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Gray),
            modifier = Modifier.padding(32.dp).size(200.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                if (!isFlipped) {
                    Image(
                        painter = painterResource(id = R.drawable.imagen_memorama1),
                        contentDescription = "Flashcard Image",
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Text(text = "Respuesta correcta",
                        modifier= Modifier.fillMaxSize().wrapContentSize(Alignment.Center),
                        textAlign = TextAlign.Center)
                }
            }
        }
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                onAnswerSelected("Respuesta 1")
                setFlipped(true)}) {
                Text(text = "Respuesta 1")

            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = { onAnswerSelected("Respuesta 2")
                setFlipped(true)}) {
                Text(text = "Respuesta 2")
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (selectedAnswer.isNotEmpty()) {
                Text(text = if (selectedAnswer == correctAnswer) "¡Correcto!" else "Incorrecto, la respuesta correcta es $correctAnswer")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick={}){
                Text(text="Siguiente")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigate(AppRoutes.FlashcardDecks.route) }) {
                Text(text = "Regresar")
            }
        }
    }

