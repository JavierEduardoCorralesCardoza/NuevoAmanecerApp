package com.example.proyectonuevoamanecer.screens.flashcards

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CartaFlashEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val imagen: String,
    val texto: String,
    val resp1: String,
    val resp2: String,
    val mazoId: Int
){
    override fun toString(): String {
        return "CartaFlashEntity(id=$id, imagen='$imagen', texto='$texto', resp1='$resp1', resp2='$resp2', mazoId=$mazoId)"
    }
}