package com.example.nuevoamanecer_numeros.Numeros.viewModel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Random

class NumerosViewModel(application: Application, _nivelDificultad: Int, _Ascendente: Boolean = true) : AndroidViewModel(application) {
    val nivelDificultad = _nivelDificultad
    val Ascendente = _Ascendente
    var numbers: List<Int> = generarListaNumeros(nivelDificultad,Ascendente)
    val NumerosOprimidos = mutableStateListOf<Int>()
    val NumerosIncorrecto = mutableStateListOf<Int>()
    val score = mutableStateOf(0)
    val tiempoRestante = mutableStateOf(0 * 1000L)
    val posiciones = mutableStateMapOf<Int, Pair<Dp, Dp>>()
    val highScore = mutableStateOf(0)
    val juegoEnProgreso = mutableStateOf(true)
    val primerJuego = mutableStateOf(true)
    private val constraintsSet = mutableStateOf(false)
    private var boxWidth = 0.dp
    private var boxHeight = 0.dp
    private var textHeight = 0.dp

    fun setConstraints(boxWidth: Dp, boxHeight: Dp, textHeight: Dp) {
        this.boxWidth = boxWidth
        this.boxHeight = boxHeight
        this.textHeight = textHeight
        constraintsSet.value = true
        resetGame()
    }

    init {
        viewModelScope.launch {
            while (true) {
                delay(1000L)  // Espera 1 segundo
                if (tiempoRestante.value > 0) {
                    tiempoRestante.value -= 1000L  // Decrementa el tiempo restante
                } else if (juegoEnProgreso.value) {
                    endGame()  // Termina el juego
                }
            }
        }
    }
    fun startGame() {
        if (constraintsSet.value) {
            juegoEnProgreso.value = true  // Marca el juego como en curso
            tiempoRestante.value = 30 * 1000L  // Reinicia el tiempo restante
            resetGame()  // Reinicia el juego
        }
    }

    fun endGame() {
        juegoEnProgreso.value = false  // Marca el juego como terminado
        if (score.value > highScore.value) {
            highScore.value = score.value  // Actualiza la puntuación alta
            score.value = 0
        }
    }

    private fun generarListaNumeros(nivelDificultad: Int, ordenAscendente: Boolean = true): List<Int> {
        val numeros = when (nivelDificultad) {
            1 -> (1..10).toList()
            2 -> (1..50).shuffled().take(10).toList()
            3 -> (-100..100).shuffled().take(15).toList()
            else -> (1..10).toList()
        }
        return if (ordenAscendente) {
            numeros.sorted()
        } else {
            numeros.sortedDescending()
        }
    }

    fun addNumber(number: Int) {
        val siguienteNumeroEsperado = if (NumerosOprimidos.size < numbers.size) {
            numbers[NumerosOprimidos.size]
        } else {
            Int.MIN_VALUE // Valor que no existe
        }

        if (number == siguienteNumeroEsperado) {
            NumerosOprimidos.add(number)
            NumerosIncorrecto.remove(number)
            if (NumerosOprimidos.size == numbers.size) {
                score.value += 500  // Incrementa la puntuación en 500 si se oprimen todos los números
                tiempoRestante.value += 15 * 1000L
                resetGame()
            }
        } else if (!NumerosOprimidos.contains(number) && !NumerosIncorrecto.contains(number)) {
            NumerosIncorrecto.add(number)
            score.value -= 10
            tiempoRestante.value -= 1 * 1000L
        }
    }
    fun resetGame() {
        if (!juegoEnProgreso.value) {
            return  // No reinicia el juego si ya ha terminado
        }
        NumerosOprimidos.clear()
        NumerosIncorrecto.clear()
        numbers = generarListaNumeros(nivelDificultad,Ascendente)
        // Generar nuevas posiciones aleatorias para los números
        val random = Random()
        posiciones.clear()
        numbers.forEach { number ->
            val offsetX = (random.nextInt((boxWidth - textHeight).value.toInt() * 2) - (boxWidth - textHeight).value.toInt()).dp
            val offsetY = (random.nextInt((boxHeight - textHeight).value.toInt() * 2) - (boxHeight - textHeight).value.toInt()).dp
            posiciones[number] = Pair(offsetX, offsetY)
        }
    }
}
