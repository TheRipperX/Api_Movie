package com.example.filmapp.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.filmapp.databinding.ItemDetailImagesBinding
import javax.inject.Inject

class AdapterDetail @Inject constructor(): RecyclerView.Adapter<AdapterDetail.ViewHolderDetail>() {

    private lateinit var binding: ItemDetailImagesBinding

    inner class ViewHolderDetail: RecyclerView.ViewHolder(binding.root) {

        fun setDataItem(item: String) {

            binding.apply {
                imgDetailLastImage.load(item){
                    crossfade(true)
                    crossfade(700)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderDetail {
        val layout = LayoutInflater.from(parent.context)
        binding = ItemDetailImagesBinding.inflate(layout, parent, false)
        return ViewHolderDetail()
    }

    override fun onBindViewHolder(holder: ViewHolderDetail, position: Int) {
        holder.setDataItem(differ.currentList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val differCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }

    var differ = AsyncListDiffer(this, differCallback)
}