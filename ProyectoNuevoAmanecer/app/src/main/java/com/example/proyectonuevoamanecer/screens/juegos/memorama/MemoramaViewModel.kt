package com.example.proyectonuevoamanecer.screens.juegos.memorama

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.proyectonuevoamanecer.R
import com.example.proyectonuevoamanecer.clases.CartasMemorama

class MemoramaViewModel : ViewModel() {
    val cartas = mutableStateListOf<CartasMemorama>(
        CartasMemorama(imagen = R.drawable.frente_carta, volteada = false),
        CartasMemorama(imagen = R.drawable.frente_carta, volteada = false)
    )

    fun voltearCarta(index: Int) {
        cartas[index].volteada = !cartas[index].volteada
    }
}