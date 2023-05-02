package com.example.lab2_3.model

import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.io.Serializable

data class Breed(
    val name: String,
    val subBreeds: List<String>?,
    var img: String?
): Serializable

suspend fun getBreeds(): List<Breed>? {
    val client = OkHttpClient()
    val request = Request.Builder()
        .url("https://dog.ceo/api/breeds/list/all")
        .build()

    return try {
        val response = client.newCall(request).execute()
        val responseData = response.body()?.string()
        if (response.isSuccessful && responseData != null) {
            val breeds = mutableListOf<Breed>()
            val jsonObject = JSONObject(responseData)
            val message = jsonObject.getJSONObject("message")
            for (key in message.keys()) {
                val subBreedsJsonArray = message.getJSONArray(key)
                val subBreedsList = mutableListOf<String>()
                for (i in 0 until subBreedsJsonArray.length()) {
                    subBreedsList.add(subBreedsJsonArray.getString(i))
                }
                val breed = Breed(key, if (subBreedsList.isEmpty()) null else subBreedsList, null)
                breeds.add(breed)

                // запускаем запрос на получение случайного изображения для породы
                val imgRequest = Request.Builder()
                    .url("https://dog.ceo/api/breed/$key/images/random")
                    .build()
                client.newCall(imgRequest).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        breed.img = null
                    }

                    override fun onResponse(call: Call, response: Response) {
                        val responseData = response.body()?.string()
                        if (response.isSuccessful && responseData != null) {
                            val jsonObject = JSONObject(responseData)
                            breed.img = jsonObject.getString("message")
                        } else {
                            breed.img = null
                        }
                    }
                })
            }
            breeds
        } else {
            null
        }
    } catch (e: Exception) {
        null
    }
}


/*
выполняется запрос к API сайта dog.ceo для получения списка всех пород собак и случайного изображения для каждой породы.
Функция getBreeds() использует библиотеку OkHttp для создания и выполнения запросов. Сначала создается объект client класса OkHttpClient. Затем создается запрос request для получения списка всех пород собак,
 используя URL https://dog.ceo/api/breeds/list/all.
Далее выполняется запрос с помощью client.newCall(request).execute(), и полученный ответ обрабатывается. Если запрос успешен и ответ содержит данные, то создается пустой список breeds типа MutableList<Breed>.
Для каждой породы в списке ответа выполняется запрос на получение случайного изображения, используя URL https://dog.ceo/api/breed/<breed>/images/random, где <breed> - это название породы.
Для выполнения запроса на получение изображения используется метод client.newCall(imgRequest).enqueue(object : Callback {...}), который запускает запрос в отдельном потоке.
Полученные данные о породах собак и их случайных изображениях сохраняются в объектах класса Breed, которые добавляются в список breeds.
В итоге функция getBreeds() возвращает список всех пород собак со случайными изображениями для каждой породы в виде списка объектов класса Breed.
*/
