package com.example.proyectonuevoamanecer.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ripple.rememberRipple

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyectonuevoamanecer.R

@Composable
fun BotonFlashcards(text: String, click: () -> Unit, imgCard: Painter, back: Color, size: Float){
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    Card(
        modifier = Modifier
            .padding(16.dp)
            .clickable(onClick = click)
            .fillMaxWidth()
            ,

        //shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
        ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = back)
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(

                painter = imgCard,
                contentDescription = "flashcards",
                modifier = Modifier
                    .padding(top = 18.dp, bottom = 6.dp, start = 18.dp, end = 18.dp)
                    .height(screenWidth*size)
            )

            Text(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                ,
                text = text,
                color = Color.White,
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold
            )
        }

    }
}

@Preview
@Composable
fun previewBtn(){
    val imgCard = painterResource(id = R.drawable.flashcards)

    BotonFlashcards("Tarjetas \n Educativas", {}, imgCard, Color(0x66, 0x00, 0x99), 0.5f)
}
