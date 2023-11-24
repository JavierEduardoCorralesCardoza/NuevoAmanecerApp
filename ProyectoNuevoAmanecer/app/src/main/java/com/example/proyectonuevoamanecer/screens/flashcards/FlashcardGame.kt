

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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.proyectonuevoamanecer.R
import com.example.proyectonuevoamanecer.clases.CartaFlash
import com.example.proyectonuevoamanecer.clases.Mazos
import com.example.proyectonuevoamanecer.clases.processTTS
import com.example.proyectonuevoamanecer.screens.AppRoutes
import com.example.proyectonuevoamanecer.screens.config.ConfiguracionViewModel
import com.example.proyectonuevoamanecer.ui.theme.ProyectoNuevoAmanecerTheme
/*@Preview(showBackground = true)
@Composable
fun PreviewFlashcardGame() {
    val navController = rememberNavController()
    FlashcardGame(navController)

}
*/


@Composable
fun FlashcardGame(navController: NavController, mazo : String){

    val mazo = remember { mutableStateOf(generateDeck(mazo)) }
    BodyGameContent(navController, mazo)
}
fun generateDeck(mazo: String): Mazos {
    return when (mazo) {
        "Animales"-> {
            val cardList = mutableListOf<CartaFlash>(
            CartaFlash(R.drawable.imagen_memorama1, "As", "As", "Rey"),
            CartaFlash(R.drawable.imagen_memorama2, "Magic", "Rey", "Magic"),
            CartaFlash(R.drawable.imagen_memorama3, "Uno", "Uno", "Dos"),
            CartaFlash(R.drawable.imagen_memorama4, "Nibbles", "Needles", "Nibbles")
        ).apply { shuffle() }
            Mazos("Animales" ,cardList)
    }
    "Objetos"-> {
        val cardList2 = mutableListOf<CartaFlash>(
            CartaFlash(R.drawable.imagen_memorama5, "Pokemon", "Libre", "Pokemon"),
            CartaFlash(R.drawable.imagen_memorama6, "Yugio", "Yugio", "Chainsaw"),
            CartaFlash(R.drawable.imagen_memorama7, "Española", "Española", "Sueca"),
            CartaFlash(R.drawable.imagen_memorama8, "Lovers", "Amantes", "Lovers")
        ).apply { shuffle() }
        Mazos("Objetos", cardList2)
    }
        else ->{
            Mazos("Animales", mutableListOf())

        }
    }

}


@Composable
fun BodyGameContent(navController: NavController, mazo:MutableState<Mazos>) {

    Text(
        text = mazo.value.titulo,
        style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center
    )
    val (isAnswerSelected, setAnswerSelected) = remember { mutableStateOf(false) }
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }
    val (currentIndex, setCurrentIndex) = remember { mutableStateOf(0) }
    val currentCard = mazo.value.flashcardList[currentIndex]
    val correctAnswer = currentCard.texto
    val (isFlipped, setFlipped) = remember { mutableStateOf(false) }
    val (selectedAnswer, onAnswerSelected)= remember {
        mutableStateOf("")
    }
    if (showDialog){
        AlertDialog(
            onDismissRequest ={setShowDialog(false)} ,
            title = {Text("¡Ya acabaste el mazo!")},
            confirmButton = {
                Button(onClick = {
                    setCurrentIndex(0)
                    setFlipped(false)
                    onAnswerSelected("")
                    mazo.value= generateDeck(mazo.value.titulo)
                    setShowDialog(false)
                }) {
                    Text("Volver a iniciar")
                }
            },
            dismissButton = {
                Button(onClick = {
                    setShowDialog(false)
                    navController.navigate(AppRoutes.FlashcardDecks.route)
                }) {
                    Text("Regresar")
                }
            })
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
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()

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

            val context = LocalContext.current
            val viewModelConfig: ConfiguracionViewModel = viewModel()
            Button(onClick = {
                processTTS(context, currentCard.resp1, viewModelConfig)
                onAnswerSelected(currentCard.resp1)
                setFlipped(true)
                setAnswerSelected(true)
            }) {
                Text(text = currentCard.resp1)

            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
                processTTS(context, currentCard.resp2, viewModelConfig)
                onAnswerSelected(currentCard.resp2)
                setFlipped(true)
                setAnswerSelected(true)
            }) {
                Text(text = currentCard.resp2)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (selectedAnswer.isNotEmpty()) {
            Text(text = if (selectedAnswer == correctAnswer) "¡Correcto! La Respuesta es $correctAnswer"
            else "Incorrecto, la respuesta correcta es $correctAnswer")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if(isAnswerSelected) {
                if (currentIndex < mazo.value.flashcardList.size - 1) {
                    setCurrentIndex(currentIndex + 1)
                } else {
                    setShowDialog(true)
                }
                setFlipped(false)
                onAnswerSelected("")
                setAnswerSelected(false)
            }
        }, enabled = isAnswerSelected) {
            Text(text = "Siguiente")
        }

    }

}


