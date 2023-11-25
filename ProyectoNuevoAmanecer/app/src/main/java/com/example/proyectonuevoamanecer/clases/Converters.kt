package com.example.proyectonuevoamanecer.clases

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromFlashcardList(flashcardList: MutableList<CartaFlash>): String {
        val gson = Gson()
        return gson.toJson(flashcardList)
    }

    @TypeConverter
    fun toFlashcardList(flashcardListString: String): MutableList<CartaFlash> {
        val gson = Gson()
        val type = object : TypeToken<MutableList<CartaFlash>>() {}.type
        return gson.fromJson(flashcardListString, type)
    }
}
