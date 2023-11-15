package com.example.proyectonuevoamanecer.screens.flashcards

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FlashViewModel(private val database: FlashcardDatabase) : ViewModel() {

    private val dao: FlashcardDao = database.flashcardDao()

    val allMazos: LiveData<List<MazoEntity>> = dao.getAllMazos().asLiveData()

    fun getCartasFlashFromMazo(mazoId: Int): LiveData<List<CartaFlashEntity>> {
        return dao.getCartasFlashFromMazo(mazoId).asLiveData()
    }

    fun insertMazo(mazo: MazoEntity) = viewModelScope.launch(Dispatchers.IO) {
        dao.insertMazo(mazo)
    }

    fun insertCartaFlash(cartaFlash: CartaFlashEntity) = viewModelScope.launch(Dispatchers.IO) {
        dao.insertCartaFlash(cartaFlash)
    }
}
