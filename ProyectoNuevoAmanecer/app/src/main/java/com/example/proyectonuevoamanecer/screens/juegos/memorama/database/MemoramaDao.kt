package com.example.proyectonuevoamanecer.screens.juegos.memorama.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MemoramaDao {
    @Insert
    fun insert(carta: CartaEntity)

    @Delete
    fun delete(carta: CartaEntity)

    @Query("SELECT * FROM CartaEntity")
    fun getAllImages(): Flow<List<CartaEntity>>
}