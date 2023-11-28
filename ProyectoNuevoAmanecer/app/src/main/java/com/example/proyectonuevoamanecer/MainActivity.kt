package com.example.proyectonuevoamanecer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.navigation.compose.rememberNavController
import com.example.proyectonuevoamanecer.databases.DbDatabase
import com.example.proyectonuevoamanecer.screens.modalUI.ModalUi
import com.example.proyectonuevoamanecer.ui.theme.ProyectoNuevoAmanecerTheme
import com.example.proyectonuevoamanecer.widgets.Gif

class MainActivity : ComponentActivity() {
    lateinit var db: DbDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = DbDatabase.getInstance(this)
        setContent {

            ProyectoNuevoAmanecerTheme {
                // A surface container using the 'background' color from the theme

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BoxWithConstraints(
                        modifier = Modifier.fillMaxSize()

                    ) {
                        val constraints = maxWidth / maxHeight
                        val imagePainter = painterResource(id = R.drawable.galaxy_doodle2)
                        Image(
                            painter = imagePainter,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    color = MaterialTheme.colorScheme.background)

                        )
                        Gif(gif = R.drawable.estrellas,
                            modifier = Modifier
                                .fillMaxSize()
                                .absoluteOffset(y = (-maxHeight/3))
                        )
                        Gif(gif = R.drawable.estrellas,
                            modifier = Modifier
                                .fillMaxSize()
                                .absoluteOffset(y = maxHeight/3)
                        )


                        ModalUi(rememberNavController())
                    }
                }
            }
        }
    }
}

