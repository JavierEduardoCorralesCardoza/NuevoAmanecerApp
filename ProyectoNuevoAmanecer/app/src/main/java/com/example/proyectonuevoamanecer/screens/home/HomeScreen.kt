package com.example.proyectonuevoamanecer.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.platform.LocalConfiguration
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
    val screenWidth = LocalConfiguration.current.screenWidthDp.toFloat()

    Column {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            item { BotonFlashcards(
                text = "Minijuegos",
                click = { navController.navigate(route = AppRoutes.JuegosScreen.route) } ,
                imgCard = imgCard,
                back = Color(0x99, 0x00, 0x55),
                //img = screenWidth/1.8f

            ) }
            item { BotonFlashcards(
                text = "Tarjetas Educativas",
                {navController.navigate(AppRoutes.MainFlashMenu.route)},
                imgCard2,
                Color(0x00, 0x4C, 0x99),
                //img = screenWidth/1f
            ) }

        }

    }


}

