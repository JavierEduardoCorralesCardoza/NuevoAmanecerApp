package com.example.proyectonuevoamanecer.screens.flashcards

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.proyectonuevoamanecer.clases.Converters

@Database(entities = [MazoEntity::class, CartaFlashEntity::class], version = 2)
@TypeConverters(Converters::class)
abstract class FlashcardDatabase : RoomDatabase() {
    abstract fun flashcardDao(): FlashcardDao

    companion object {
        @Volatile
        private var INSTANCE: FlashcardDatabase? = null

        fun getInstance(context: Context): FlashcardDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FlashcardDatabase::class.java,
                    "flashcard_database"
                ).fallbackToDestructiveMigration() // Este método se utiliza para realizar una migración destructiva
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}