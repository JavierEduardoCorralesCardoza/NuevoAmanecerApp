package com.example.proyectonuevoamanecer.screens.juegos.memorama.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class DBViewModel(private val database: MemoramaDatabase) : ViewModel() {

    private val dao: MemoramaDao = database.MemoramaDao()

    val allImages: Flow<List<CartaEntity>> = dao.getAllImages()

    fun insertCarta(carta: CartaEntity) = viewModelScope.launch(Dispatchers.IO) {
        dao.insert(carta)
    }

    fun deleteCarta(carta: CartaEntity) = viewModelScope.launch(Dispatchers.IO) {
        dao.delete(carta)
    }
}