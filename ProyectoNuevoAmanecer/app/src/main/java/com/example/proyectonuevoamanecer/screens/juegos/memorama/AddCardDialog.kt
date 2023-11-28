package com.example.proyectonuevoamanecer.screens.juegos.memorama

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.proyectonuevoamanecer.screens.flashcards.CartaFlashEntity
import com.example.proyectonuevoamanecer.screens.flashcards.copyImageToInternalStore
import com.example.proyectonuevoamanecer.screens.juegos.memorama.database.CartaEntity

@Composable
fun AddCardDialog(showDialog: MutableState<Boolean>, onAddCard: (CartaEntity) -> Unit) {
    if (showDialog.value) {
        var imagen by remember { mutableStateOf("") }
        var texto by remember { mutableStateOf("") }

        val context = LocalContext.current
        val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            // Aquí puedes guardar la Uri de la imagen en imageUri
            if(uri != null) {
                val filePath= copyImageToInternalStore(context,uri)
                imagen = filePath
            }
        }

        AlertDialog(
            onDismissRequest = {
                showDialog.value = false
            },
            title = {
                Text(text = "Añadir Tarjeta")
            },
            text = {
                Column {
                    TextField(
                        value = texto,
                        onValueChange = { texto = it },
                        label = { Text("Nombre de la imagen") }
                    )
                    Button(onClick = {
                        launcher.launch("image/*")
                    }) {
                        Text("Añadir Foto")
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        // Aquí puedes crear una nueva CartaFlashEntity con la información recogida
                        // y llamar a onAddCard con la nueva tarjeta
                        val newCard = CartaEntity(0, imagen, texto)
                        onAddCard(newCard)
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