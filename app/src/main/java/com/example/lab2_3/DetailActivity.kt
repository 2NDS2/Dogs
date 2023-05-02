package com.example.lab2_3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.lab2_3.databinding.ActivityDetailBinding
import com.example.lab2_3.model.Breed

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val breed = intent.getSerializableExtra("Breed") as Breed

        // Устанавливаем данные в соответствующие элементы макета
        binding.tvBreed.text = breed.name
        Glide.with(this)
            .load(breed.img) // путь к изображению из объекта Breed
            .diskCacheStrategy(DiskCacheStrategy.ALL) // сохранение изображения в кэше
            .into(binding.tvImg) // установка изображения в ImageView
    }
}