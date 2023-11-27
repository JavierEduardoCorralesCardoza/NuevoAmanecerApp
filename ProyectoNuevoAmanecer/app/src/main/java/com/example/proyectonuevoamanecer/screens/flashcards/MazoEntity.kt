package com.example.proyectonuevoamanecer.screens.flashcards

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.proyectonuevoamanecer.clases.CartaFlash
import com.example.proyectonuevoamanecer.clases.Mazos


@Entity
data class MazoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val titulo: String,
) {
    override fun toString(): String {
        return "MazoEntity(id=$id, titulo='$titulo')"
    }
}