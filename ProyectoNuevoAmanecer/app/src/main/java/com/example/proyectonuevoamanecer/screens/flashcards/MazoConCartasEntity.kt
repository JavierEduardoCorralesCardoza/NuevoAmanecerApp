package com.example.proyectonuevoamanecer.screens.flashcards

import androidx.room.Embedded
import androidx.room.Relation

data class MazoConCartasEntity(

    @Embedded
    val mazo: MazoEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "mazoId"
    )
    val cartas: List<CartaFlashEntity>
)
