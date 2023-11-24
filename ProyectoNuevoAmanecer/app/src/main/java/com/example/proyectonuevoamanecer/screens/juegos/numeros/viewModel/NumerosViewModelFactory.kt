package com.example.nuevoamanecer_numeros.Numeros.viewModel

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModel

class NumerosViewModelFactory(private val application: Application,private val nivelDificultad: Int, private val ascendente: Boolean = true, private val timed: Boolean) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NumerosViewModel::class.java)) {
            return NumerosViewModel(application,nivelDificultad,ascendente,timed) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}