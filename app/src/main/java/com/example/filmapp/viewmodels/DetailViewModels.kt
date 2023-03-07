package com.example.filmapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmapp.db.MoviesEntity
import com.example.filmapp.models.detail.ResponseDetails
import com.example.filmapp.repository.DetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModels @Inject constructor(private val repository: DetailRepository): ViewModel() {

    //api
    val detailMovieData = MutableLiveData<ResponseDetails>()
    val loading = MutableLiveData<Boolean>()

    fun detailMovie(id: Int) = viewModelScope.launch {
        loading.postValue(true)

        val response = repository.detailMovies(id)

        if (response.isSuccessful) {
            detailMovieData.postValue(response.body())
        }

        loading.postValue(false)
    }

    //database
    val isFav = MutableLiveData<Boolean>()

    suspend fun existMove(id: Int) = withContext(viewModelScope.coroutineContext) {

        repository.existData(id)
    }

    fun favMovie(id: Int, entity: MoviesEntity) = viewModelScope.launch {

        val exist = repository.existData(id)

        if (exist) {
            isFav.postValue(false)
            repository.deleteData(entity)
        }else {
            isFav.postValue(true)
            repository.insertData(entity)
        }
    }
}