package com.example.proyectonuevoamanecer.screens.flashcards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FlashViewModelFactory(private val database: FlashcardDatabase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlashViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FlashViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
