package com.example.proyectonuevoamanecer.screens.flashcards

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteCardDialog(showDialog: MutableState<Boolean>, cartas: List<CartaFlashEntity>, onDeleteCard: (CartaFlashEntity) -> Unit){
    if(showDialog.value){
        AlertDialog(
            onDismissRequest = {
                showDialog.value = false
            },
            confirmButton = { /*TODO*/ },
            title = {
                Text(text = "Eliminar Tarjeta")
            },
            text = {
                LazyColumn(){
                    items(cartas){carta ->
                        Row {
                            Text(text = carta.texto)
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