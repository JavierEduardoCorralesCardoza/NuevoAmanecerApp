package com.example.proyectonuevoamanecer.screens.flashcards

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CartaFlashEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val imagen: Int,
    val texto: String,
    val resp1: String,
    val resp2: String,
    val mazoId: Int
)