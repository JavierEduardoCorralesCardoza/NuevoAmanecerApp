package com.example.proyectonuevoamanecer.screens.juegos.memorama

import android.app.Application
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectonuevoamanecer.R
import com.example.proyectonuevoamanecer.clases.CartasMemorama
import com.example.proyectonuevoamanecer.screens.juegos.memorama.database.CartaEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Collections

class MemoramaViewModel(application: Application) : AndroidViewModel(application) {

    val imagenes_carta = mutableListOf<String>()
    val cartas = mutableListOf<CartasMemorama>()
    val indexCartasVolteadas = mutableListOf<Int>()
    val retrasoEnEjecucion = mutableStateOf(false)
    val cuadroDeDialogo = mutableStateOf(false)
    val score = mutableIntStateOf(50)

    fun ListaDeImagenes(cartas: List<CartaEntity>){
        if(imagenes_carta.isEmpty()) {
            for (i in 0..cartas.size-1) { // Ajusta este rango según la cantidad de imágenes que tienes
                imagenes_carta.add(cartas[i].path)
            }
        }
    }
    fun GenerarCartas(numCartas: Int){
        if(cartas.isEmpty()) {
            for (i in 0 until numCartas) {
                cartas.add(CartasMemorama(imagen = imagenes_carta[i], volteada = false))
                cartas.add(CartasMemorama(imagen = imagenes_carta[i], volteada = false))
            }
            cartas.shuffle()
        }
    }

    fun voltearCartasConRetraso(cartas: List<CartasMemorama>, indexCartasVolteadas: MutableList<Int>) {
        viewModelScope.launch {
            retrasoEnEjecucion.value = true
            delay(500)  // Retraso de 1 segundo
            cartas[indexCartasVolteadas[0]].volteada = false
            cartas[indexCartasVolteadas[1]].volteada = false
            indexCartasVolteadas.clear()
            retrasoEnEjecucion.value = false
        }
    }
}

