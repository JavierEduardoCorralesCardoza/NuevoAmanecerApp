package com.example.proyectonuevoamanecer.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.proyectonuevoamanecer.R
import com.example.proyectonuevoamanecer.screens.AppRoutes
import com.example.proyectonuevoamanecer.screens.config.Configuracion
import com.example.proyectonuevoamanecer.screens.config.ConfiguracionViewModel
import com.example.proyectonuevoamanecer.widgets.BotonFlashcards

@Composable
fun HomeScreen(navController: NavController){
    BodyContent(navController)
}

@Composable
fun BodyContent(navController: NavController){
    val configViewModel:ConfiguracionViewModel = viewModel()
    val imgCard2 = painterResource(id = R.drawable.flashcards)
    val imgCard = painterResource(id = R.drawable.minijuegos)
    val density = LocalDensity.current.density

    Column {
        /*Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Luminaria")
            IconButton(
                onClick = {  configViewModel.configuracionAbierta.value = true  },
                modifier = Modifier
                    .padding(16.dp)
                    .background(color = Color.Gray, shape = CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Settings, // Puedes cambiar el ícono según tus necesidades
                    contentDescription = "Configuración",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(24.dp * density) // Ajusta el tamaño del ícono según tus necesidades
                )
            }

        }*/

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            BotonFlashcards(
                text = "Minijuegos",
                click = { navController.navigate(route = AppRoutes.JuegosScreen.route) } ,
                imgCard = imgCard,
                back = Color(0xCC, 0x00, 0x00),
                0.5f
            )
            BotonFlashcards(
                text = "Tarjetas \n Educativas",
                {navController.navigate(AppRoutes.MainFlashMenu.route)},
                imgCard2,
                Color(0x00, 0x4C, 0x99),
                0.5f
            )
        }

    }

    Configuracion()
}

