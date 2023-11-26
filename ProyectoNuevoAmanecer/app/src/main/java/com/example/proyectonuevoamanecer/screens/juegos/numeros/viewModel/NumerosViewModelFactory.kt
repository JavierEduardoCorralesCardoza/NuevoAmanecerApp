package com.example.proyectonuevoamanecer.screens.juegos.numeros.viewModel

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModel
import com.example.proyectonuevoamanecer.screens.juegos.numeros.configuracion.Configuracion

class NumerosViewModelFactory(private val application: Application,private val configuracion: Configuracion,private val timed: Boolean) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NumerosViewModel::class.java)) {
            return NumerosViewModel(application, configuracion,timed) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}