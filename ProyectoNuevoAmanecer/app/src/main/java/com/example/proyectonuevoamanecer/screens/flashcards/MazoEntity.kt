package com.example.proyectonuevoamanecer.screens.flashcards

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class MazoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val titulo: String
)