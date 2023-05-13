package com.example.lab2_3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.lab2_3.adapter.DogAdapter
import com.example.lab2_3.databinding.ActivityMainBinding
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.lab2_3.bd.BreedDao
import com.example.lab2_3.bd.BreedDatabase
import com.example.lab2_3.bd.BreedEntity
import com.example.lab2_3.model.Breed
import com.example.lab2_3.model.BreedsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: DogAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var database: BreedDatabase
    lateinit var BreedDao: BreedDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//

        // Создаем экземпляр базы данных
        this.database = Room.databaseBuilder(
            applicationContext,
            BreedDatabase::class.java, "breeds-database"
        ).build()



        //BreedDao.insert(BreedEntity())

        initial()
    }

    private fun initial() {
        // Находим RecyclerView в макете и устанавливаем на него адаптер
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

//           supportFragmentManager
//            .beginTransaction()
//            .add(R.id.detailF, DetailFragment.newInstance())
//            .commit()
        })

        // Получаем объект dao из базы данных
        val breedsDao = database.breedsDao()

// Передаем dao и контекст MainActivity в конструктор BreedsRepository
        val breedsRepository = BreedsRepository(breedsDao, this)

// Запускаем асинхронную операцию, чтобы получить список пород собак
        lifecycleScope.launch(Dispatchers.IO) {
            breedsRepository.getBreeds({ breeds ->
                // Обновляем список пород в адаптере
                adapter.newSet(ArrayList(breeds))
                // Выполняем уведомление об изменении данных
                runOnUiThread {
                    adapter.notifyDataSetChanged()
                }
            }, {})
        }
    }
}

// передача данных из бд в рекуклервью
// для второго тоже пподобное придумать что и с первым