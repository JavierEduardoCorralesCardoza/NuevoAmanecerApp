package com.example.proyectonuevoamanecer.screens.juegos.numeros.viewModel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectonuevoamanecer.screens.juegos.numeros.configuracion.Configuracion
import com.example.proyectonuevoamanecer.screens.juegos.numeros.configuracion.Nivel
import com.example.proyectonuevoamanecer.screens.juegos.numeros.configuracion.Orden
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Random
import kotlin.math.abs

class NumerosViewModel(
    application: Application,
    configuracion: Configuracion,
    val Timed: Boolean
) : AndroidViewModel(application) {
    var niveles = configuracion.niveles
    var nivelActual = 0
    var setsCompletados = 0
    var ordenActual: String? = null
    var tiempoInicial = configuracion.tiempoInicial
    var tiempoAgregar = configuracion.tiempoAgregar
    var tiempoClickIncorrecto = configuracion.tiempoClickIncorrecto
    var puntosClickCorrecto = configuracion.puntosClickCorrecto
    var puntosClickInCorrecto = configuracion.puntosClickIncorrecto
    var puntosCompletarSet = configuracion.puntosCompletarSet
    var numbers: List<Int> = generarListaNumeros(niveles,nivelActual)
    val coloresNumeros = mutableStateMapOf<Int, Color>()
    val NumerosOprimidos = mutableStateListOf<Int>()
    val NumerosIncorrecto = mutableStateListOf<Int>()
    val score = mutableStateOf(0)
    val tiempoRestante = mutableStateOf(0 * 1000L)
    val posiciones = mutableStateMapOf<Int, Pair<Dp, Dp>>()
    val highScoreTimed = mutableStateOf(0)
    val highScoreNormal = mutableStateOf(0)
    val juegoEnProgreso = mutableStateOf(false)
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
        if (Timed) {
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
    }
    fun startGame() {
        if (constraintsSet.value) {
            juegoEnProgreso.value = true  // Marca el juego como en curso
            if (Timed) {
                tiempoRestante.value = tiempoInicial * 1000L}  // Reinicia el tiempo restante
            setsCompletados = 0
            resetGame()  // Reinicia el juego
        }
    }

    fun endGame() {
        juegoEnProgreso.value = false  // Marca el juego como terminado
        if (score.value > if (Timed) highScoreTimed.value else highScoreNormal.value) {
            if (Timed) {
                highScoreTimed.value = score.value  // Actualiza la puntuación alta del modo temporizado
            } else {
                highScoreNormal.value = score.value  // Actualiza la puntuación alta del modo normal
            }
            score.value = 0
            nivelActual = 0
            setsCompletados = 0
        }
    }

    private fun generarListaNumeros(niveles: List<Nivel>, nivelActual: Int): List<Int> {
        val nivel = niveles[nivelActual]
        val numeros = (nivel.rango).shuffled().take(nivel.cantidadNumeros).toList()
        return when (nivel.orden) {
            Orden.ASC -> {
                ordenActual = "ASC"
                numeros.sorted()
            }
            Orden.DESC -> {
                ordenActual = "DESC"
                numeros.sortedDescending()
            }
            Orden.AL -> {
                if (kotlin.random.Random.nextBoolean()) {
                    ordenActual = "ASC"
                    numeros.sorted()
                } else {
                    ordenActual = "DESC"
                    numeros.sortedDescending()
                }
            }
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
            coloresNumeros[number] = Color.Green
            score.value += puntosClickCorrecto
            if (NumerosOprimidos.size == numbers.size) {
                score.value += puntosCompletarSet  // Incrementa la puntuación si se oprimen todos los números
                tiempoRestante.value += tiempoAgregar * 1000L
                setsCompletados++
                if (setsCompletados % 2 == 0) {
                    nivelActual++
                    if (nivelActual >= niveles.size) {
                        endGame()
                    }
                }
                resetGame()
            }
        } else if (!NumerosOprimidos.contains(number) && !NumerosIncorrecto.contains(number)) {
            NumerosIncorrecto.add(number)
            score.value -= puntosClickInCorrecto
            coloresNumeros[number] = Color.Red
            tiempoRestante.value -= tiempoClickIncorrecto * 1000L
        }
    }
    fun resetGame() {
        if (!juegoEnProgreso.value) {
            return  // No reinicia el juego si ya ha terminado
        }
        NumerosOprimidos.clear()
        NumerosIncorrecto.clear()
        coloresNumeros.clear()
        numbers = generarListaNumeros(niveles,nivelActual)
        // Generar nuevas posiciones aleatorias para los números
        val random = Random()
        posiciones.clear()
        numbers.forEach { number ->
            var offsetX: Dp
            var offsetY: Dp
            do {
                offsetX = (random.nextInt((boxWidth - textHeight).value.toInt() * 2) - (boxWidth - textHeight).value.toInt()).dp
                offsetY = (random.nextInt((boxHeight - textHeight).value.toInt() * 2) - (boxHeight - textHeight).value.toInt()).dp
            } while (posiciones.values.any { abs(it.first.value - offsetX.value) <= textHeight.value && abs(it.second.value - offsetY.value) <= textHeight.value })
            posiciones[number] = Pair(offsetX, offsetY)
        }
    }
}
