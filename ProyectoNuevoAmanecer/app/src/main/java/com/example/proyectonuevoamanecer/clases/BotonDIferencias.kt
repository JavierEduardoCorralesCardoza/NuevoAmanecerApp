package com.example.proyectonuevoamanecer.clases

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import com.example.proyectonuevoamanecer.R

class BotonDiferencias(minX: Float = 0f, minY: Float = 0f, maxX: Float = 0f, maxY: Float = 0f, active: Boolean = true) {
    var minX by mutableStateOf(minX)
    var minY by mutableStateOf(minY)
    var maxX by mutableStateOf(maxX)
    var maxY by mutableStateOf(maxY)
    var active by mutableStateOf(active)
}

