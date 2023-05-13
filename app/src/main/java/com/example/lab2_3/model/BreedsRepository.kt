package com.example.lab2_3.model

import android.content.Context
import androidx.room.Room
import com.example.lab2_3.bd.BreedDao
import com.example.lab2_3.bd.BreedDatabase
import com.example.lab2_3.bd.BreedEntity
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class BreedsRepository(private val breedDao: BreedDao, private val context: Context) {
    private val client = OkHttpClient()
    private var database:  BreedDatabase = Room.databaseBuilder(
        this.context.applicationContext,
        BreedDatabase::class.java, "breed_database"
    ).build()

    // Функция для получения списка пород
    // onSuccess - колбэк для успешного завершения запроса
    // onError - колбэк для ошибки при выполнении запроса
    fun getBreeds(onSuccess: (List<Breed>) -> Unit, onError: () -> Unit) {
        val request = Request.Builder()
            .url("https://dog.ceo/api/breeds/list/all")
            .build()

        // Выполняем запрос на получение списка пород
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                onError()
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body()?.string()
                if (response.isSuccessful && responseData != null) {
                    val breeds = mutableListOf<Breed>()
                    val jsonObject = JSONObject(responseData)
                    val message = jsonObject.getJSONObject("message")

                    // Проходимся по всем породам в полученном списке
                    for (key in message.keys()) {
                        // Создаем объект породы
                        val breed = Breed(key, null/*, null*/)
                        breeds.add(breed)

                        // Запрашиваем случайное изображение для породы
                        val imgRequest = Request.Builder()
                            .url("https://dog.ceo/api/breed/$key/images/random")
                            .build()

                        // Выполняем запрос на получение изображения
                        client.newCall(imgRequest).enqueue(object : Callback {
                            override fun onFailure(call: Call, e: IOException) {
                                breed.img = null
                            }

                            override fun onResponse(call: Call, response: Response) {
                                val responseData = response.body()?.string()
                                if (response.isSuccessful && responseData != null) {
                                    val jsonObject = JSONObject(responseData)
                                    breed.img = jsonObject.getString("message")
                                    // сохранение данных в таблицу
                                    database.breedsDao().insert(BreedEntity(name = breed.name,
                                        img = breed.img))

                                } else {
                                    breed.img = null
                                }
                            }
                        })
                    }
                    // Вызываем колбэк onSuccess и передаем список пород
                    onSuccess(breeds)
                } else {
                    // Вызываем колбэк onError в случае ошибки
                    onError()
                }
            }
        })
    }
}
