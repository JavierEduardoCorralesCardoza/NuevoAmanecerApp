package com.example.proyectonuevoamanecer.screens.flashcards

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCardDialog(showDialog: MutableState<Boolean>, mazoId: Int, onAddCard: (CartaFlashEntity) -> Unit) {
    if (showDialog.value) {
        var imagen by remember { mutableStateOf("") }
        var texto by remember { mutableStateOf("") }
        var resp1 by remember { mutableStateOf("") }
        var resp2 by remember { mutableStateOf("") }

        val context = LocalContext.current
        val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            // Aquí puedes guardar la Uri de la imagen en imageUri
            if(uri != null) {
                val filePath= copyImageToInternalStore(context,uri)
                imagen = filePath
            }
        }

        AlertDialog(
            containerColor=Color.White,
            shape= RoundedCornerShape(12.dp),
            onDismissRequest = {
                showDialog.value = false
            },
            title = {
                Text(text = "Añadir Tarjeta", textAlign = TextAlign.End,style= TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),color=Color.Black)
            },
            text = {
                Column {
                    Button(
                        colors=ButtonDefaults.buttonColors(Color.Blue),
                        shape= RoundedCornerShape(12.dp),
                        onClick = {
                        launcher.launch("image/*")
                    }) {
                        Text("Añadir Foto",
                            color=Color.White,
                            style= TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        )
                    }
                    TextField(
                        value = texto,
                        onValueChange = { texto = it },
                        label = { Text("Respuesta") }
                    )
                    TextField(
                        value = resp1,
                        onValueChange = { resp1 = it },
                        label = { Text("Opción 1") }
                    )
                    TextField(
                        value = resp2,
                        onValueChange = { resp2 = it },
                        label = { Text("Opción 2") }
                    )
                }
            },
            confirmButton = {
                Button(
                    colors=ButtonDefaults.buttonColors(Color.Blue),
                    shape= RoundedCornerShape(12.dp),
                    onClick = {
                        // Aquí puedes crear una nueva CartaFlashEntity con la información recogida
                        // y llamar a onAddCard con la nueva tarjeta
                        val newCard = CartaFlashEntity(0, imagen, texto, resp1, resp2, mazoId)
                        onAddCard(newCard)
                        showDialog.value = false
                    }
                ) {
                    Text("Confirmar",
                        color=Color.White,
                        style= TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold))
                }
            },
            dismissButton = {
                Button(
                    colors=ButtonDefaults.buttonColors(Color.Blue),
                    shape= RoundedCornerShape(12.dp),
                    onClick = {
                        showDialog.value = false
                    }
                ) {
                    Text("Cancelar",
                        color=Color.White,
                        style= TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold))
                }
            }
        )
    }
}
