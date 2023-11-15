package com.example.nuevoamanecer_numeros.Numeros.viewModel

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModel

class NumerosViewModelFactory(private val application: Application,private val nivelDificultad: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NumerosViewModel::class.java)) {
            return NumerosViewModel(application,nivelDificultad) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}