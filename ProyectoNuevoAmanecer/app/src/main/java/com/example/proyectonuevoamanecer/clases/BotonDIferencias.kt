package com.example.proyectonuevoamanecer.clases

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.proyectonuevoamanecer.R

class BotonDiferencias(sizeX: Int = 30, sizeY: Int = 30, posX: Int, posY: Int) {
    var siseX by mutableStateOf(sizeX)
    var siseY by mutableStateOf(sizeY)
    var posX by mutableStateOf(posX)
    var posY by mutableStateOf(posY)

}

