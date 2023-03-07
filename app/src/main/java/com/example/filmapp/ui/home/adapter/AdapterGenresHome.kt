package com.example.filmapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.databinding.ItemGenresMoviesBinding
import com.example.filmapp.models.home.ResponseGenres
import javax.inject.Inject

class AdapterGenresHome @Inject constructor(): RecyclerView.Adapter<AdapterGenresHome.ViewHolder>() {

    private lateinit var binding: ItemGenresMoviesBinding

    inner class ViewHolder: RecyclerView.ViewHolder(binding.root) {

        fun getItems(item: ResponseGenres.GenresHomeMovieItem) {
            binding.apply {
                textGenresItems.text = item.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context)
        binding = ItemGenresMoviesBinding.inflate(layout, parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.getItems(differ.currentList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val defferCallBack = object : DiffUtil.ItemCallback<ResponseGenres.GenresHomeMovieItem>() {
        override fun areItemsTheSame(
            oldItem: ResponseGenres.GenresHomeMovieItem,
            newItem: ResponseGenres.GenresHomeMovieItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ResponseGenres.GenresHomeMovieItem,
            newItem: ResponseGenres.GenresHomeMovieItem
        ): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, defferCallBack)
}