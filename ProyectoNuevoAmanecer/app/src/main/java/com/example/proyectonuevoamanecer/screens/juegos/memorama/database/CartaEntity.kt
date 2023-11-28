package com.example.proyectonuevoamanecer.screens.juegos.memorama.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CartaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val path: String,
    val nombre: String
)
