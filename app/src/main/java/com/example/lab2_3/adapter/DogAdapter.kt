package com.example.lab2_3.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lab2_3.databinding.ItemDogLayoutBinding
import com.example.lab2_3.model.Breed

class DogAdapter : RecyclerView.Adapter<DogAdapter.DogViewHolder>() {

    private var breeds = emptyList<Breed>()
    inner class DogViewHolder(internal val binding: ItemDogLayoutBinding): RecyclerView.ViewHolder(binding.root)

    // Обработчик клика на CardView
    interface OnItemClickListener {
        fun onItemClick(dogModel: Breed)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val binding =
            ItemDogLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        val currentDog = breeds[position]
        with(holder.binding) {
            tvBreed.text = currentDog.name
            Glide.with(root.context)
                .load(currentDog.img)
                .into(tvImg)
            // Добавляем обработчик клика на CardView
            cardView.setOnClickListener {
                listener?.onItemClick(currentDog)
            }
        }
    }

    override fun getItemCount(): Int {
        return breeds.size
    }

    fun newSet(list : ArrayList<Breed>){
        println(list)
        breeds = list
    }
}