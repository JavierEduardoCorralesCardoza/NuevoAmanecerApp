package com.example.proyectonuevoamanecer.screens.config

import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel

class ConfiguracionViewModel(application: Application): AndroidViewModel(application = application) {
    private val sharedPreferences = application.getSharedPreferences("config", Context.MODE_PRIVATE)

    val configuracionAbierta = mutableStateOf(false)
    val brillo = mutableFloatStateOf(0.5f)
    val volumen = mutableFloatStateOf(0.5f)
    val tts = mutableStateOf(sharedPreferences.getBoolean("tts", true))

    fun saveState() {
        with (sharedPreferences.edit()) {
            putBoolean("tts", tts.value)
            apply()
        }
    }
}