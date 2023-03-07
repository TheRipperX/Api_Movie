package com.example.filmapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmapp.models.search.ResponseSearch
import com.example.filmapp.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModels @Inject constructor(private val repository: SearchRepository): ViewModel() {

    val searchLiveData = MutableLiveData<ResponseSearch>()
    val loading = MutableLiveData<Boolean>()
    val emptyList = MutableLiveData<Boolean>()


    fun searchMoviesList(name: String) = viewModelScope.launch {

        loading.postValue(true)

        val response = repository.searchMovies(name)

        if (response.isSuccessful) {

            if (response.body()?.data!!.isNotEmpty()) {
                searchLiveData.postValue(response.body())
                emptyList.postValue(false)
            }else{
                emptyList.postValue(true)
            }
        }

        loading.postValue(false)
    }

}