package com.example.lab2_3.bd

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "breeds")
data class BreedEntity(
    @PrimaryKey//(autoGenerate = false)
    val id: Int? = null,
    @ColumnInfo(name="name")
    var name: String,
    @ColumnInfo(name="img")
    var img: String?
)
