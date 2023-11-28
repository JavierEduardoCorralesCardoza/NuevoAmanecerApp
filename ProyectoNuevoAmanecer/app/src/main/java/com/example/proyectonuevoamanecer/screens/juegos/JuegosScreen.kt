package com.example.proyectonuevoamanecer.screens.juegos
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.proyectonuevoamanecer.R
import com.example.proyectonuevoamanecer.screens.AppRoutes
import com.example.proyectonuevoamanecer.widgets.BotonFlashcards

@Composable
fun JuegosScreen(navController: NavController){
    BodyContentJuegos(navController)
}

@Composable
fun BodyContentJuegos(navController: NavController){
    val imgMemo = painterResource(id = R.drawable.memo)
    val imgNum = painterResource(id = R.drawable.numbers)
    val imgDiff = painterResource(id = R.drawable.diff)
    val imgImg = painterResource(id = R.drawable.images)
    val imgPuzzle = painterResource(id = R.drawable.puzzle)
    val imgBack = painterResource(id = R.drawable.back)
    LazyColumn(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item { Text(text = "Minijuegos") }

        item{ Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            BotonFlashcards(
                text = "Memorama",
                click = { navController.navigate("${AppRoutes.NivelesJuegos.route}/${AppRoutes.MemoramaScreen.route}") },
                imgCard = imgMemo,
                Color(0xD1, 0xCA, 0x6, 0xFF),
                0.3f,
                0.45f
            )


            BotonFlashcards(
                text = "Eleccion",
                click = {navController.navigate(AppRoutes.EligirImagen.route) },
                imgCard = imgImg,
                Color(0xD1, 0xCA, 0x6, 0xFF),
                0.3f,

                )

        }}

        item{Row {
            BotonFlashcards(
                text = "Diferencias",
                click = {navController.navigate("${AppRoutes.NivelesJuegos.route}/${AppRoutes.DiferenciasCard.route}")},
                imgCard = imgDiff,
                Color(0xD1, 0xCA, 0x6, 0xFF),
                0.3f,
                0.45f
            )
            BotonFlashcards(
                text = "Rompecabezas",
                click = {navController.navigate(AppRoutes.Rompecabezas.route) },
                imgCard = imgPuzzle,
                Color(0xD1, 0xCA, 0x6, 0xFF),
                0.3f,
            )

        }}

        item{Row {
            BotonFlashcards(
                text = "Numeros",
                click = {navController.navigate(AppRoutes.Numeros.route) },
                imgCard = imgNum,
                Color(0xD1, 0xCA, 0x6, 0xFF),
                0.3f,
                0.45f
            )
            BotonFlashcards(
                text = "Regresar",
                click = {navController.navigate(AppRoutes.HomeScreen.route) },
                imgCard = imgBack,
                Color(0xD1, 0xCA, 0x6, 0xFF),
                0.3f,
            )

        } }

    }
}