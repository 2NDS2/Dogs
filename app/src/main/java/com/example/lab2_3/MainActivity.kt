package com.example.lab2_3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val contentFragment = ContentFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, contentFragment)
            .commit()
    }
}



//
//class MainActivity : AppCompatActivity()/*, DogAdapter.OnItemClickListener*/ {
//    lateinit var binding: ActivityMainBinding
////    lateinit var adapter: DogAdapter
////    lateinit var recyclerView: RecyclerView
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
////        initial()
//    }
//
////    private fun initial() {
////        recyclerView = binding.rvDog
////        adapter = DogAdapter()
////        recyclerView.adapter = adapter
////
////        adapter.setOnItemClickListener(this)
////
////        lifecycleScope.launch(Dispatchers.IO) {
////            adapter.newSet(getBreeds() as ArrayList<Breed>)
////            runOnUiThread {
////                adapter.notifyDataSetChanged()
////            }
////        }
////    }
//
////    override fun onItemClick(dogModel: Breed) {
////        val detailFragment = DetailFragment.newInstance(dogModel)
////        supportFragmentManager.beginTransaction()
////            .replace(binding.container.id, detailFragment)
////            .addToBackStack(null)
////            .commit()
////    }
//}