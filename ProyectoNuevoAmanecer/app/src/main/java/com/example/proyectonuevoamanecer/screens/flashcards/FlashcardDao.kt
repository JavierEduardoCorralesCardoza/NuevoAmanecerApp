package com.example.proyectonuevoamanecer.screens.flashcards

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FlashcardDao {
    @Query("SELECT * FROM MazoEntity")
    fun getAllMazos(): Flow<List<MazoEntity>>

    @Query("SELECT * FROM CartaFlashEntity WHERE mazoId = :mazoId")
    fun getCartasFlashFromMazo(mazoId: Int): Flow<List<CartaFlashEntity>>
    @Insert
    fun insertMazo(mazo: MazoEntity): Long

    @Insert
    fun insertCartaFlash(cartaFlash: CartaFlashEntity)

}