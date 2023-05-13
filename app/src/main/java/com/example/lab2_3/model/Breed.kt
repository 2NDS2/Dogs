package com.example.lab2_3.model

import java.io.Serializable

data class Breed(
    val name: String,
   // val subBreeds: List<String>?, // подпороды, но они мне не нужны
    var img: String?,
): Serializable