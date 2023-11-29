package com.example.proyectonuevoamanecer.screens.flashcards

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearMazoDialog(showDialog: MutableState<Boolean>, onMazoCreado:
(String)->Unit){
    if (showDialog. value){
        var mazoNombre by remember {
            mutableStateOf("")}
        AlertDialog(
            containerColor=Color.White,
            shape= RoundedCornerShape(12.dp),
            onDismissRequest = { showDialog.value = false },
            title = {Text("Elija el nombre del mazo",textAlign = TextAlign.End,style= TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),color=Color.Black)},
            text= {
                TextField(
                    value = mazoNombre,
                    onValueChange = {newValue -> mazoNombre = newValue},
                    label = {Text("Nombre del mazo")}
                )
            },
            confirmButton = {
                Button(colors=ButtonDefaults.buttonColors(Color.Blue),
                    shape= RoundedCornerShape(12.dp),
                    onClick={
                        onMazoCreado(mazoNombre)
                        showDialog.value = false
                    }
                ){
                    Text("Crear Mazo",
                        color= Color.White,
                        style= TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    )
                }
            },
            dismissButton = {
                Button(colors= ButtonDefaults.buttonColors(Color.Blue),
                    shape= RoundedCornerShape(12.dp),
                    onClick = {showDialog.value = false}){
                    Text("Cancelar",
                        color=Color.White,
                        style= TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold))
                }
            }
        )
    }
}