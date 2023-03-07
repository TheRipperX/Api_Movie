package com.example.filmapp.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.filmapp.R
import com.example.filmapp.databinding.ItemLastMoviesBinding
import com.example.filmapp.db.MoviesEntity
import com.example.filmapp.models.search.ResponseSearch
import com.example.filmapp.utils.AdaptersUpdates
import javax.inject.Inject

class AdapterSearchMovies @Inject constructor(): RecyclerView.Adapter<AdapterSearchMovies.ViewHolder>(){

    private lateinit var binding: ItemLastMoviesBinding
    var emptyList = emptyList<ResponseSearch.Data>()

    inner class ViewHolder: RecyclerView.ViewHolder(binding.root) {

        fun setData(items: ResponseSearch.Data) {

            binding.apply {

                imgPoster.load(items.poster){
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

    private var onClickItem: ((ResponseSearch.Data) -> Unit)? = null

    fun setOnClickMovie(listener: (ResponseSearch.Data) -> Unit) {
        onClickItem = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context)
        binding = ItemLastMoviesBinding.inflate(layout, parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(emptyList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
        return emptyList.size
    }

    fun setDataSearch(item: List<ResponseSearch.Data>) {

        val differClass = AdaptersUpdates(emptyList, item)
        val differ = DiffUtil.calculateDiff(differClass)
        emptyList = item
        differ.dispatchUpdatesTo(this)
    }
}