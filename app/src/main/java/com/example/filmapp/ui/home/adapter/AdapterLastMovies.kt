package com.example.filmapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.filmapp.R
import com.example.filmapp.databinding.ItemLastMoviesBinding
import com.example.filmapp.db.MoviesEntity
import com.example.filmapp.models.home.ResponseLastMovies
import com.example.filmapp.utils.AdaptersUpdates
import javax.inject.Inject

class AdapterLastMovies @Inject constructor(): RecyclerView.Adapter<AdapterLastMovies.ViewHolder>() {

    private lateinit var binding: ItemLastMoviesBinding

    private var listData = emptyList<ResponseLastMovies.Data>()

    inner class ViewHolder: RecyclerView.ViewHolder(binding.root) {

        fun setData(items: ResponseLastMovies.Data) {

            binding.apply {

                imgPoster.load(items.poster){
                    error(R.drawable.empty)
                    crossfade(800)
                    crossfade(false)
                }
                txtNameVideo.text = items.title
                txtStarsVideo.text = items.imdbRating
                txtCountryVideo.text = items.country
                txtYearsVideo.text = items.year

                //click
                root.setOnClickListener {

                    onClickItem?.let { movie ->

                        movie(items)
                    }
                }
            }
        }
    }

    private var onClickItem: ((ResponseLastMovies.Data) -> Unit)? = null

    fun setOnClickMovie(listener: (ResponseLastMovies.Data) -> Unit) {
        onClickItem = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context)
        binding = ItemLastMoviesBinding.inflate(layout, parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(listData[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
        return listData.count()
    }

    fun getDataItems(item: List<ResponseLastMovies.Data>) {

        val moviesDiffUtil = AdaptersUpdates(listData, item)
        val diffUtil = DiffUtil.calculateDiff(moviesDiffUtil)
        listData = item
        diffUtil.dispatchUpdatesTo(this)

    }

}