package com.example.proyectonuevoamanecer.screens.juegos.memorama

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.proyectonuevoamanecer.R
import com.example.proyectonuevoamanecer.clases.CartasMemorama
import java.util.Collections

class MemoramaViewModel(application: Application) : AndroidViewModel(application) {

    val imagenes_carta = mutableListOf<Int>()
    val cartas = mutableListOf<CartasMemorama>()
    val cartas_volteadas = mutableMapOf<Int, Int>()
    fun ListaDeImagenes(numCartas: Int){
        if(imagenes_carta.isEmpty()) {
            for (i in 1..numCartas) { // Ajusta este rango según la cantidad de imágenes que tienes
                val id = getApplication<Application>().resources.getIdentifier(
                    "imagen_memorama$i",
                    "drawable",
                    getApplication<Application>().packageName
                )
                imagenes_carta.add(id)
            }
        }
    }
    fun GenerarCartas(numCartas: Int){
        if(cartas.isEmpty()) {
            for (i in 0 until numCartas) {
                cartas.add(CartasMemorama(imagen = imagenes_carta[i], volteada = false))
                cartas.add(CartasMemorama(imagen = imagenes_carta[i], volteada = false))
                cartas_volteadas[imagenes_carta[i]] = 0
            }
            cartas.shuffle()
        }
    }
}

