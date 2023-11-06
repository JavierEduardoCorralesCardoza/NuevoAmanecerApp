package com.example.proyectonuevoamanecer.screens.flashcards

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyectonuevoamanecer.screens.AppRoutes
import com.example.proyectonuevoamanecer.ui.theme.ProyectoNuevoAmanecerTheme
@Composable
fun MainFlashMenu(navController: NavController){
    BodyContent(navController)
}
@Composable
fun BodyContent(navController: NavController){
    Text(text="Tarjetas Educativas",
        style = TextStyle(fontSize=24.sp,fontWeight = FontWeight.Bold),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        textAlign= TextAlign.Center)
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {

            Button(
                onClick = { navController.navigate(AppRoutes.FlashcardDecks.route) },
                modifier = Modifier.padding(16.dp)
            ) {
                // Contenido del botón "Verificar Mazos"
                Text("Ver Mazos")
            }
            Button(
                onClick = {navController.navigate(AppRoutes.HomeScreen.route) },
                modifier = Modifier.padding(16.dp)
            ) {
                // Contenido del botón "Regresar"
                Text("Regresar")
            }
        }
    )
}
