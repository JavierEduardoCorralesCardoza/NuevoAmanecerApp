package com.example.proyectonuevoamanecer.screens.juegos.memorama.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CartaEntity::class], version = 2)
abstract class MemoramaDatabase : RoomDatabase() {
    abstract fun MemoramaDao(): MemoramaDao

    companion object {
        @Volatile
        private var INSTANCE: MemoramaDatabase? = null

        fun getInstance(context: Context): MemoramaDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MemoramaDatabase::class.java,
                        "memorama_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}