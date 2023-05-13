package com.example.lab2_3.bd

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface BreedDao {
    @Insert
    fun insert(breedEntity: BreedEntity)

    @Query("SELECT * FROM breeds")
    fun getAllBreeds(): List<BreedEntity>

    @Query("SELECT * FROM breeds WHERE name = :nameDog")
    fun getBreedByName(nameDog: String): BreedEntity?

}
