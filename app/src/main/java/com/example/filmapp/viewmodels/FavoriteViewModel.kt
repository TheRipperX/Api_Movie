package com.example.filmapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmapp.db.MoviesEntity
import com.example.filmapp.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: FavoriteRepository): ViewModel(){

    val favItemLiveData = MutableLiveData<MutableList<MoviesEntity>>()
    val empty = MutableLiveData<Boolean>()

    fun getFavItems() = viewModelScope.launch {

        val listFav = repository.allFavListMovie()

        if (listFav.isNotEmpty()) {
            favItemLiveData.postValue(listFav)
            empty.postValue(true)
        }
        else {
            empty.postValue(false)
        }
    }
}