package com.example.lab2_3.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lab2_3.databinding.ItemDogLayoutBinding
import com.example.lab2_3.model.DogModel

class DogAdapter : RecyclerView.Adapter<DogAdapter.DogViewHolder>() {

    private var dogList = emptyList<DogModel>()

    inner class DogViewHolder(internal val binding: ItemDogLayoutBinding): RecyclerView.ViewHolder(binding.root)

    // Обработчик клика на CardView
    interface OnItemClickListener {
        fun onItemClick(dogModel: DogModel)
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
        val currentDog = dogList[position]
        with(holder.binding) {
            tvBreed.text = currentDog.breed
            tvBreed.text = currentDog.breed

            tvImg.setImageResource(currentDog.img)

            // Добавляем обработчик клика на CardView
            cardView.setOnClickListener {
                listener?.onItemClick(currentDog)
            }
        }
    }

    override fun getItemCount(): Int {
        return dogList.size
    }

    fun setList(list: List<DogModel>) {
        dogList = list
        notifyDataSetChanged()
    }
}