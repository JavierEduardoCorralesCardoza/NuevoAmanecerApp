package com.example.proyectonuevoamanecer.clases

import androidx.room.TypeConverter
import com.example.proyectonuevoamanecer.screens.flashcards.CartaFlashEntity
import com.example.proyectonuevoamanecer.screens.flashcards.MazoEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromFlashcardList(flashcardList: MutableList<CartaFlashEntity>): String {
        val gson = Gson()
        return gson.toJson(flashcardList)
    }

    @TypeConverter
    fun toFlashcardList(flashcardListString: String): MutableList<CartaFlashEntity> {
        val gson = Gson()
        val type = object : TypeToken<MutableList<CartaFlashEntity>>() {}.type
        return gson.fromJson(flashcardListString, type)
    }
}
