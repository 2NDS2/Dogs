package com.example.lab2_3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.lab2_3.adapter.DogAdapter
import com.example.lab2_3.databinding.ActivityMainBinding
import com.example.lab2_3.model.DogModel

class MainActivity : AppCompatActivity() {

    var img = arrayOf(R.drawable.samoed, R.drawable.bgil, R.drawable.chau_chau, R.drawable.chikhuakhua, R.drawable.doberman,
        R.drawable.mops, R.drawable.pekines, R.drawable.pomeranskij_shpic, R.drawable.rotveiler, R.drawable.siba_inu)
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
            override fun onItemClick(dogModel: DogModel) {
                // Здесь открываем DetailActivity, передавая данные о выбранной собаке
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra("breed", dogModel.breed)
                intent.putExtra("dogInfo", dogModel.dogInfo)
                intent.putExtra("img", dogModel.img)
                startActivity(intent)
            }
        })

        adapter.setList(myDog())
    }

    fun myDog(): ArrayList<DogModel>{
        val dogList = ArrayList<DogModel>()

        val dogBreed = resources.getStringArray(R.array.breedName)
        val dogInfo = resources.getStringArray(R.array.dogInfo)
        for (i in dogBreed.indices){
            val dog = DogModel(dogBreed[i], img[i], dogInfo[i])
            dogList.add(dog)
        }
        return dogList
    }
}