package com.example.proyectonuevoamanecer.screens.flashcards

import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface FlashcardDao {
    @Query("SELECT * FROM MazoEntity")
    fun getAllMazos(): Flow<List<MazoEntity>>

    @Query("SELECT * FROM MazoEntity WHERE titulo = :titulo")
    fun getMazoPorNombre(titulo: String): Flow<MazoEntity>

    @Query("SELECT * FROM CartaFlashEntity WHERE texto = :texto")
    fun getCartaPorTexto(texto: String): Flow<CartaFlashEntity>

    @Query("SELECT * FROM CartaFlashEntity WHERE mazoId = :mazoId")
    fun getCartasFlashFromMazo(mazoId: Int): Flow<List<CartaFlashEntity>>
    @Insert
    fun insertMazo(mazo: MazoEntity): Long

    @Query("DELETE FROM MazoEntity WHERE id = :mazoId")
    fun deleteMazo(mazoId: Int): Int

    @Query("UPDATE MazoEntity SET titulo = :newName WHERE id = :mazoId")
    fun renameMazo(mazoId: Int, newName: String): Int

    @Insert
    fun insertCartaFlash(cartaFlash: CartaFlashEntity)

    @Transaction
    @Query("SELECT * FROM MazoEntity WHERE titulo = :titulo")
    fun getMazoConCartasPorNombre(titulo: String): Flow<MazoConCartasEntity>
}