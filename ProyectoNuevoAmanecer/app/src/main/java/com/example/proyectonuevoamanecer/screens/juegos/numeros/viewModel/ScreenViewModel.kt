package com.example.proyectonuevoamanecer.screens.juegos.numeros.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.proyectonuevoamanecer.screens.juegos.numeros.GameScreenState

class ScreenViewModel : ViewModel() {
    val currentScreen = mutableStateOf(GameScreenState.SelectLevel)

    fun onModeSelected(level: Int) {
        currentScreen.value = when (level) {
            1 -> GameScreenState.PlayNormal
            2 -> GameScreenState.PlayTimed
            else -> GameScreenState.SelectLevel
        }
    }
}
