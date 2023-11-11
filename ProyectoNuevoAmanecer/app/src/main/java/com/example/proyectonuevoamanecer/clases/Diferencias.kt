package com.example.proyectonuevoamanecer.clases

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class Diferencias(
    var image1: Int,
    var image2: Int,
    var name: String,
    var differenceNumber: Int,
    differences: MutableList<BotonDiferencias>
){
    var differences by mutableStateOf(differences)
}
