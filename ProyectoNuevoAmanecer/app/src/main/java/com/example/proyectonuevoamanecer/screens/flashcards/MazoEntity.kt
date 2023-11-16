package com.example.proyectonuevoamanecer.screens.flashcards

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.proyectonuevoamanecer.clases.CartaFlash
import com.example.proyectonuevoamanecer.clases.Mazos


@Entity
data class MazoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val titulo: String,
    val flashcardList:MutableList<CartaFlash>
)