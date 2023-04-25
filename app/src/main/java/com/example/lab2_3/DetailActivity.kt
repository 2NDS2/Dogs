package com.example.lab2_3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lab2_3.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Получаем данные о породе, картинке и описании собаки из Intent
        val breed = intent.getStringExtra("breed")
        val img = intent.getIntExtra("img", 0)
        val dogInfo = intent.getStringExtra("dogInfo")

        // Устанавливаем данные в соответствующие элементы макета
        binding.tvBreed.text = breed
        binding.tvImg.setImageResource(img)
        binding.tvInfo.text = dogInfo
    }
}