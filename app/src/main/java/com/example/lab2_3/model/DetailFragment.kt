package com.example.lab2_3.model

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.lab2_3.databinding.FragmentDetailBinding
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding

    companion object {
        private const val ARG_BREED = "breed"

        fun newInstance(breed: Breed): DetailFragment {
            val fragment = DetailFragment()
            val args = Bundle()
            args.putSerializable(ARG_BREED, breed)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val breed = arguments?.getSerializable(ARG_BREED) as? Breed

        breed?.let {
            binding.tvBreed.text = it.name
            loadImage(binding.tvImg, it)
            loadImage(binding.tvImg2, it)
            loadImage(binding.tvImg3, it)
            loadImage(binding.tvImg4, it)
            loadImage(binding.tvImg5, it)
            loadImage(binding.tvImg6, it)
            loadImage(binding.tvImg7, it)
        }
    }
    private fun loadImage(imageView: ImageView, breed: Breed) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://dog.ceo/api/breed/${breed.name}/images/random")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Обработка ошибки, если запрос не удался
                breed.img = null
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body()?.string()
                if (response.isSuccessful && responseData != null) {
                    val jsonObject = JSONObject(responseData)
                    val imgUrl = jsonObject.getString("message") // Получаем URL изображения из ответа
                    breed.img = imgUrl // Сохраняем URL изображения в объекте породы

                    // Загрузка изображения с помощью библиотеки Glide
                    activity?.runOnUiThread {
                        Glide.with(requireContext())
                            .load(imgUrl)
                            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                            .into(imageView) // Отображение изображения в ImageView
                    }
                } else {
                    breed.img = null
                }
            }
        })
    }
}