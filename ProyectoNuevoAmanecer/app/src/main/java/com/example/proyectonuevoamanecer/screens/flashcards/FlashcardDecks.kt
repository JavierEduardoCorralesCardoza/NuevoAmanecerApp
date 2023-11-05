package com.example.proyectonuevoamanecer.screens.flashcards

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
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
    var expanded by remember { mutableStateOf(false)}
    val options = listOf("Borrar Mazo", "Agregar Tarjetas", "Cambiar Nombre")
    Text(text="Mazos",
        style = TextStyle(fontSize=24.sp,fontWeight = FontWeight.Bold),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
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
        Box{
            Button(
                onClick={expanded=true},
                modifier = Modifier.padding(16.dp)
            ){
                Icon(imageVector= Icons.Default.MoreVert,contentDescription = null)
            }
            MyDropdownMenu(
                expanded = expanded,
                options = options,
                onOptionSelected = { option ->
                    when (option) {
                        "Borrar Mazo" -> {
                            // Lógica para "Borrar Mazo"
                        }
                        "Agregar Tarjetas" -> {
                            // Lógica para "Agregar Tarjetas"
                        }
                        "Cambiar Nombre" -> {
                            // Lógica para "Cambiar Nombre"
                        }
                    }
                    expanded = false
                }
            )
        }
        // Otros botones de mazos aquí...

        if (selectedDeck != null) {
            // Mostrar un mensaje o navegar a la actividad que muestra las tarjetas del mazo seleccionado
        }
    }
}

@Composable
fun MyDropdownMenu(
    expanded: Boolean,
    options: List<String>,
    onOptionSelected: (String) -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { /* No cambia el estado de 'expanded' aquí */ }
    ) {
        options.forEach { option ->
            DropdownMenuItem(onClick = {
                onOptionSelected(option)
            }) {
                Text(text = option)
            }
        }
    }
}




