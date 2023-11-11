package com.example.proyectonuevoamanecer.screens.config

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel

class ConfiguracionViewModel(application: Application):AndroidViewModel(application = application) {
    val configuracionAbierta = mutableStateOf(false)
    val brillo = mutableStateOf(0.5f)
    val volumen = mutableStateOf(0.5f)
}