package com.example.proyectonuevoamanecer.screens.flashcards

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearMazoDialog(showDialog: MutableState<Boolean>, onMazoCreado:
(String)->Unit){
    if (showDialog. value){
        var mazoNombre by remember {
            mutableStateOf(TextFieldValue(""))}
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = {Text("Elija el nombre del mazo")},
            text= {
                TextField(
                    value = mazoNombre,
                    onValueChange = {newValue -> mazoNombre = newValue},
                    label = {Text("Nombre del mazo")}
                )
            },
            confirmButton = {
                Button(
                    onClick={
                        onMazoCreado(mazoNombre.text)
                        showDialog.value = false
                    }
                ){
                    Text("Crear Mazo")
                }
            },
            dismissButton = {
                Button(onClick = {showDialog.value = false}){
                    Text("Cancelar")
                }
            }
        )
    }
}