package com.example.proyectonuevoamanecer.screens.flashcards

import android.graphics.Paint
import android.text.Layout
import android.text.Layout.Alignment
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteCardDialog(showDialog: MutableState<Boolean>, cartas: List<CartaFlashEntity>, onDeleteCard: (CartaFlashEntity) -> Unit){
    if(showDialog.value){
        AlertDialog(
            containerColor=Color.White,
            shape= RoundedCornerShape(12.dp),
            onDismissRequest = {
                showDialog.value = false
            },
            confirmButton = { /*TODO*/ },
            title = {
                Text(text = "Eliminar Tarjeta", textAlign = TextAlign.End,style= TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),color=Color.Black)
            },
            text = {
                LazyColumn(){
                    items(cartas){carta ->
                        Row (modifier=Modifier
                            .background(Color.Black.copy(alpha=0.1f))
                            .padding(2.dp)
                            .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            ){

                            Text(text = carta.texto,color=Color.Black, style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp))
                            Spacer(modifier=Modifier.width(30.dp))
                            Button(colors= ButtonDefaults.buttonColors(Color.Blue),
                                shape= RoundedCornerShape(12.dp),
                                onClick = { onDeleteCard(carta) }) {
                                Text(text = "Eliminar",
                                    color=Color.White,
                                    style= TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                )
                            }
                        }
                    }
                }
            },
            dismissButton = {
                Button(colors=ButtonDefaults.buttonColors(Color.Blue),
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