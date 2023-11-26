package com.example.proyectonuevoamanecer.screens.flashcards

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
//import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.proyectonuevoamanecer.clases.Mazos
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class FlashViewModel(private val database: FlashcardDatabase) : ViewModel() {

    private val dao: FlashcardDao = database.flashcardDao()

    val allMazos: Flow<List<MazoEntity>> = dao.getAllMazos()

    fun getCartasFlashFromMazo(mazoId: Int): Flow<List<CartaFlashEntity>> {
        return dao.getCartasFlashFromMazo(mazoId)
    }


    fun insertMazo(mazo: MazoEntity) = viewModelScope.launch(Dispatchers.IO) {
        dao.insertMazo(mazo)
    }
    fun deleteMazo(mazoId: Int,mazos: List<MazoEntity>) = viewModelScope.launch(Dispatchers.IO) {
        dao.deleteMazo(mazoId)
    }
    fun renameMazo(mazoId: Int, newName: String) = viewModelScope.launch(Dispatchers.IO) {
        dao.renameMazo(mazoId, newName)
    }

    fun insertCartaFlash(cartaFlash: CartaFlashEntity) = viewModelScope.launch(Dispatchers.IO) {
        dao.insertCartaFlash(cartaFlash)
    }
}
