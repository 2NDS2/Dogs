package com.example.lab2_3.bd

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BreedEntity::class], version = 1)
abstract class BreedDatabase : RoomDatabase() {
    abstract fun breedsDao(): BreedDao


    companion object{
        fun getDatabase(context: Context): BreedDatabase{
            return Room.databaseBuilder(
                context.applicationContext,
                BreedDatabase::class.java,
                "breed-database"
            ).build()
        }
    }
}