package com.example.proyectonuevoamanecer.screens.juegos.memorama.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DBViewModelFactory(private val database: MemoramaDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DBViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DBViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}