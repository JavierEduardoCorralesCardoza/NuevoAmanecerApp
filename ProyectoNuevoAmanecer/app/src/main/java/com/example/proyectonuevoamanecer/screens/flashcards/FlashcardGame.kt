

package com.example.proyectonuevoamanecer.screens.flashcards

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.example.proyectonuevoamanecer.clases.CartaFlash
import com.example.proyectonuevoamanecer.clases.Mazos
import com.example.proyectonuevoamanecer.screens.AppRoutes
import com.example.proyectonuevoamanecer.ui.theme.ProyectoNuevoAmanecerTheme
/*
@Preview(showBackground = true)
@Composable
fun PreviewFlashcardGame() {
    val navController = rememberNavController()
    FlashcardGame(navController)

}

 */

@Composable
fun FlashcardGame(navController: NavController, deck : String){
    val cardList = mutableListOf<CartaFlash>(
        CartaFlash(R.drawable.imagen_memorama1,"Ass","As","Rey"),
        CartaFlash(R.drawable.imagen_memorama2,"Magic","Rey","Magic"),
        CartaFlash(R.drawable.imagen_memorama3,"Uno","Uno","Dos"),
        CartaFlash(R.drawable.imagen_memorama4,"Nibbles", "Nipples","Nibbles")
    )
    val mazo = Mazos("Cartas",cardList)
    BodyGameContent(navController, mazo)
}

@Composable
fun BodyGameContent(navController: NavController, deck: Mazos) {

    Text(
        text = deck.titulo,
        style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center
    )
    val (currentIndex, setCurrentIndex) = remember { mutableStateOf(0) }
    val currentCard = deck.flashcardList[currentIndex]
    val correctAnswer = currentCard.texto
    val (isFlipped, setFlipped) = remember { mutableStateOf(false) }
    val (selectedAnswer, onAnswerSelected)= remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Gray),
            modifier = Modifier
                .padding(32.dp)
                .size(200.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                if (!isFlipped) {
                    Image(painterResource(id = currentCard.imagen), contentDescription = null)

                } else {
                    Text(text = currentCard.texto)
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Button(onClick = {
                onAnswerSelected(currentCard.resp1)
                setFlipped(true)
            }) {
                Text(text = currentCard.resp1)

            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
                onAnswerSelected(currentCard.resp2)
                setFlipped(true)
            }) {
                Text(text = currentCard.resp2)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (selectedAnswer.isNotEmpty()) {
            Text(text = if (selectedAnswer == correctAnswer) "¡Correcto!"
            else "Incorrecto, la respuesta correcta es $correctAnswer")
        }
        Spacer(modifier = Modifier.height(16.dp))
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = {
            if (currentIndex < deck.flashcardList.size -1){
                setCurrentIndex(currentIndex + 1)
            }
            setFlipped(false)
        }) {
            Text(text = "Siguiente")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            setCurrentIndex(0)
            setFlipped(false)
            onAnswerSelected("")
        }) {
            Text(text = "Volver a Iniciar")
        }
    }
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = { navController.navigate(AppRoutes.FlashcardDecks.route) }) {
            Text(text = "Regresar")
        }
    }
}

