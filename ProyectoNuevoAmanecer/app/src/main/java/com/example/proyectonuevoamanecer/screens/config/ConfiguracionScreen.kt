package com.example.proyectonuevoamanecer.screens.config

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.media.AudioManager
import android.view.Window
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogWindowProvider
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun Configuracion(){
    val configViewModel:ConfiguracionViewModel = viewModel()
    val context = LocalContext.current
    val window = context.findWindow()
    val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    if(configViewModel.configuracionAbierta.value){
        Dialog(onDismissRequest = {}) {
            Card(
                colors = CardDefaults.cardColors(contentColor = Color.White, containerColor = Color.Transparent),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(focusedElevation = 1.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { configViewModel.configuracionAbierta.value = false }) {
                        Icon(Icons.Default.Close,contentDescription = "Cerrar")
                    }
                    Text(text = "Configuracion",style = MaterialTheme.typography.headlineMedium)
                }
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
                Text(text = "Texto a voz")
                Switch(
                    checked = configViewModel.tts.value,
                    onCheckedChange = {
                        configViewModel.tts.value = it
                        configViewModel.saveState()
                    }
                )
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