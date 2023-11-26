package com.example.proyectonuevoamanecer.clases

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Usuario(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val clave: String,
)