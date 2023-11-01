package com.example.proyectonuevoamanecer.screens.flashcards

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyectonuevoamanecer.ui.theme.ProyectoNuevoAmanecerTheme

class FlashcardDecks  : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProyectoNuevoAmanecerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DecksFlashcards()
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun DecksFlashcards(){
    var selectedDeck by remember { mutableStateOf<String?>(null) }
    Text(text="Mazos",
        style = TextStyle(fontSize=24.sp,fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(16.dp).fillMaxWidth(),
        textAlign= TextAlign.Center)
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        Button(
            onClick = { /* Acción para seleccionar el primer mazo */ },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Mazo 1")
        }

        Button(
            onClick = { /* Acción para seleccionar el segundo mazo */ },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Mazo 2")
        }

        // Otros botones de mazos aquí...

        if (selectedDeck != null) {
            // Mostrar un mensaje o navegar a la actividad que muestra las tarjetas del mazo seleccionado
        }
    }
}