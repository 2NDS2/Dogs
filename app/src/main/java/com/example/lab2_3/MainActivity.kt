package com.example.lab2_3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.lab2_3.adapter.DogAdapter
import com.example.lab2_3.databinding.ActivityMainBinding

import androidx.lifecycle.lifecycleScope
import com.example.lab2_3.model.Breed
import com.example.lab2_3.model.getBreeds
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: DogAdapter
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initial()
    }

    private fun initial() {
        recyclerView = binding.rvDog
        adapter = DogAdapter()
        recyclerView.adapter = adapter

        // Устанавливаем обработчик клика на CardView
        adapter.setOnItemClickListener(object : DogAdapter.OnItemClickListener {
            override fun onItemClick(dogModel: Breed) {
                // Здесь открываем DetailActivity, передавая данные о выбранной собаке
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra("Breed", dogModel)
                startActivity(intent)
            }
        })
        lifecycleScope.launch(Dispatchers.IO) {
            adapter.newSet(getBreeds() as ArrayList<Breed>)
            runOnUiThread {
                adapter.notifyDataSetChanged()
            }
        }
    }
}