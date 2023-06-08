package com.example.lab2_3

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lab2_3.adapter.DogAdapter
import com.example.lab2_3.databinding.FragmentContentBinding
import com.example.lab2_3.model.Breed
import com.example.lab2_3.model.DetailFragment
import com.example.lab2_3.model.getBreeds
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContentFragment : Fragment(), DogAdapter.OnItemClickListener {
    private lateinit var binding: FragmentContentBinding
    private lateinit var adapter: DogAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.rvDog
        adapter = DogAdapter()
        recyclerView.adapter = adapter

        adapter.setOnItemClickListener(this)

        lifecycleScope.launch(Dispatchers.IO) {
            adapter.newSet(getBreeds() as ArrayList<Breed>)
            requireActivity().runOnUiThread {
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onItemClick(dogModel: Breed) {
        val detailFragment = DetailFragment.newInstance(dogModel)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, detailFragment)
            .addToBackStack(null)
            .commit()
    }
}
