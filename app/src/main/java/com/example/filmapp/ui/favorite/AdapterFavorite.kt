package com.example.filmapp.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.filmapp.R
import com.example.filmapp.databinding.ItemLastMoviesBinding
import com.example.filmapp.db.MoviesEntity
import com.example.filmapp.utils.AdaptersUpdates
import javax.inject.Inject

class AdapterFavorite @Inject constructor(): RecyclerView.Adapter<AdapterFavorite.ViewHolder>() {

    private lateinit var binding: ItemLastMoviesBinding

    private var listData = emptyList<MoviesEntity>()

    inner class ViewHolder: RecyclerView.ViewHolder(binding.root) {

        fun setData(items: MoviesEntity) {

            binding.apply {

                imgPoster.load(items.imgMovie){
                    error(R.drawable.empty)
                    crossfade(800)
                    crossfade(false)
                }
                txtNameVideo.text = items.titleMovie
                txtStarsVideo.text = items.starsMovie
                txtCountryVideo.text = items.countryMovie
                txtYearsVideo.text = items.yearMovie

                //click
                root.setOnClickListener {

                    onClickItem?.let { movie ->
                        movie(items)
                    }
                }
            }
        }
    }

    private var onClickItem: ((MoviesEntity) -> Unit)? = null

    fun setOnClickMovie(listener: (MoviesEntity) -> Unit) {
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

    fun getDataItems(item: List<MoviesEntity>) {

        val moviesDiffUtil = AdaptersUpdates(listData, item)
        val diffUtil = DiffUtil.calculateDiff(moviesDiffUtil)
        listData = item
        diffUtil.dispatchUpdatesTo(this)

    }

}