package com.example.proyectonuevoamanecer.screens.config

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.media.AudioManager
import android.view.Window
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyectonuevoamanecer.R

@Composable
fun Configuracion(){
    val configViewModel:ConfiguracionViewModel = viewModel()
    val context = LocalContext.current
    val window = context.findWindow()
    val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    if(configViewModel.configuracionAbierta.value){
        Dialog(onDismissRequest = {}) {
            Column {
                Text(text = "Configuracion")
                Text(text = "Brillo")
                Slider(
                    value = configViewModel.brillo.value,
                    onValueChange = {
                        configViewModel.brillo.value = it
                        val params = window.attributes
                        params.screenBrightness = it
                        window.attributes = params},
                    valueRange = 0f..1f
                )
                Text(text = "Volumen")
                Slider(
                    value = configViewModel.volumen.value,
                    onValueChange = {
                        configViewModel.volumen.value = it
                        val volumeLevel = configViewModel.volumen.value * audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
                        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volumeLevel.toInt(), 0)
                    })
                Button(onClick = { configViewModel.configuracionAbierta.value = false }) {
                    Text(text = "Cerrar")
                }
            }
        }
    }
}

fun Context.findWindow(): Window {
    val context = this
    while (context is ContextWrapper) {
        if (context is Activity) {
            return context.window
        }
    }
    throw IllegalStateException("The context is not an Activity.")
}