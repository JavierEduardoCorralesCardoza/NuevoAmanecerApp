package com.example.proyectonuevoamanecer.clases

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.example.proyectonuevoamanecer.R

class CartasMemorama(volteada: Boolean, imagen: String){
    var volteada by mutableStateOf(volteada)
    var imagen by mutableStateOf(imagen)
}