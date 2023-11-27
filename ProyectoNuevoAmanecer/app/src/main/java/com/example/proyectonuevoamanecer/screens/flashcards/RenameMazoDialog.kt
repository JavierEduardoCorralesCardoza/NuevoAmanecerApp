package com.example.proyectonuevoamanecer.screens.flashcards

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RenameMazoDialog(showDialog: MutableState<Boolean>, mazoId: Int, onRename: (String) -> Unit) {
    if (showDialog.value) {
        var newName by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = {
                showDialog.value = false
            },
            title = {
                Text(text = "Renombrar Mazo")
            },
            text = {

                TextField(
                    value = newName,
                    onValueChange = { newName = it },
                    label = { Text("Nuevo nombre") }
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onRename(newName)
                        showDialog.value = false
                    }
                ) {
                    Text("Confirmar")
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
