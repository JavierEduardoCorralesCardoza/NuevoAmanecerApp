package com.example.proyectonuevoamanecer.screens.juegos.memorama.database

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.proyectonuevoamanecer.screens.flashcards.CartaFlashEntity

@Composable
fun DeleteCardDialog(showDialog: MutableState<Boolean>, cartas: List<CartaEntity>, onDeleteCard: (CartaEntity) -> Unit){
    if(showDialog.value){
        AlertDialog(
            onDismissRequest = {
                showDialog.value = false
            },
            confirmButton = { /*TODO*/ },
            title = {
                Text(text = "Eliminar Carta")
            },
            text = {
                LazyColumn(){
                    items(cartas){carta ->
                        Row {
                            Text(text = carta.nombre)
                            Button(onClick = { onDeleteCard(carta) }) {
                                Text(text = "Eliminar")
                            }
                        }
                    }
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showDialog.value = false
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}