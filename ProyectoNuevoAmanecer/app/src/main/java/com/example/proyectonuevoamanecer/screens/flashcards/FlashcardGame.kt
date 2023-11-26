

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.proyectonuevoamanecer.R
import com.example.proyectonuevoamanecer.clases.CartaFlash
import com.example.proyectonuevoamanecer.clases.Mazos
import com.example.proyectonuevoamanecer.clases.processTTS
import com.example.proyectonuevoamanecer.screens.AppRoutes
import com.example.proyectonuevoamanecer.ui.theme.ProyectoNuevoAmanecerTheme
import kotlinx.coroutines.flow.first
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModel
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.launch
import coil.compose.rememberImagePainter

@Composable
fun FlashcardGame(navController: NavController, mazoTitulo : String, viewModel: FlashViewModel){
    val mazoState = remember{ mutableStateOf<Mazos?>(null)}
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(mazoTitulo){
        coroutineScope.launch{
            val mazos = viewModel.allMazos.first()
            val mazoEntity = mazos.find{it.titulo==mazoTitulo}
            if(mazoEntity != null){
                mazoState.value=Mazos(mazoEntity.id,mazoEntity.titulo,mazoEntity.flashcardList)
            }
        }
    }
    val mazoValue = mazoState.value
    if(mazoValue != null) {
        BodyGameContent(navController, mazoValue , viewModel)
    }
}





@Composable
fun BodyGameContent(navController: NavController, mazo:Mazos, viewModel: FlashViewModel) {

    Text(
        text = mazo.titulo,
        style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center
    )
    val flashcards = viewModel.getCartasFlashFromMazo(mazo.id).collectAsState(initial = emptyList())
    val (isAnswerSelected, setAnswerSelected) = remember { mutableStateOf(false) }
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }
    val (currentIndex, setCurrentIndex) = remember { mutableStateOf(0) }
    val currentCard = flashcards.value[currentIndex]
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
                    Image(
                        painter= rememberAsyncImagePainter(model = currentCard.imagen),
                        contentDescription = null
                    )

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
            Button(onClick = {
                processTTS(context, currentCard.resp1)
                onAnswerSelected(currentCard.resp1)
                setFlipped(true)
                setAnswerSelected(true)
            }) {
                Text(text = currentCard.resp1)

            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
                processTTS(context, currentCard.resp2)
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
                if (currentIndex < flashcards.value.size - 1) {
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


