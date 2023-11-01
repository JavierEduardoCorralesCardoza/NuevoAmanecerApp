package com.example.proyectonuevoamanecer.screens.flashcards

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyectonuevoamanecer.ui.theme.ProyectoNuevoAmanecerTheme

class MainFlashMenu  : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProyectoNuevoAmanecerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val onDeckCheckClick = {
                        val intent = Intent(this, FlashcardDecks::class.java)
                        startActivity(intent)
                    }

                    val onBackClick = {
                        val intent = Intent(this, PantallaVacia::class.java)
                        startActivity(intent)
                    }

                    FlashcardMenu(onDeckCheckClick,onBackClick)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FlashcardMenu( onDeckCheckClick:() -> Unit = {}, onBackClick: () -> Unit = {}){
    Text(text="Tarjetas Educativas",
        style = TextStyle(fontSize=24.sp,fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(16.dp).fillMaxWidth(),
        textAlign= TextAlign.Center)
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {

            Button(
                onClick = { onDeckCheckClick() },
                modifier = Modifier.padding(16.dp)
            ) {
                // Contenido del botón "Verificar Mazos"
                Text("Ver Mazos")
            }
            Button(
                onClick = { onBackClick() },
                modifier = Modifier.padding(16.dp)
            ) {
                // Contenido del botón "Regresar"
                Text("Regresar")
            }
        }
    )
}
