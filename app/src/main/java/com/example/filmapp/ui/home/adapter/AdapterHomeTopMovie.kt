package com.example.filmapp.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.filmapp.R
import com.example.filmapp.databinding.ItemsHomeTopLayoutBinding
import com.example.filmapp.models.home.ResponseMovieLists
import javax.inject.Inject

class AdapterHomeTopMovie @Inject constructor(): RecyclerView.Adapter<AdapterHomeTopMovie.ViewModelHome>() {

    private lateinit var binding: ItemsHomeTopLayoutBinding

    inner class ViewModelHome: RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun getItems(item: ResponseMovieLists.Data) {
            binding.apply {
                //image top movie
                imgTopMovie.load(item.poster) {
                    error(R.drawable.empty)
                    crossfade(true)
                    crossfade(800)
                }
                textVideoName.text = item.title

                txtInfoVideo.text = "${item.imdbRating} | ${item.country} | ${item.year}"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewModelHome {
        val layout = LayoutInflater.from(parent.context)
        binding = ItemsHomeTopLayoutBinding.inflate(layout, parent, false)
        return ViewModelHome()
    }

    override fun onBindViewHolder(holder: ViewModelHome, position: Int) {
        holder.getItems(differ.currentList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
        return if (differ.currentList.size > 5) {
            5
        }else{
            differ.currentList.size
        }
    }

    private val differCallBack = object : DiffUtil.ItemCallback<ResponseMovieLists.Data>() {
        override fun areItemsTheSame(
            oldItem: ResponseMovieLists.Data,
            newItem: ResponseMovieLists.Data
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ResponseMovieLists.Data,
            newItem: ResponseMovieLists.Data
        ): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallBack)
}