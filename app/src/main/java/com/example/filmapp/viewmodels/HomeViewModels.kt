package com.example.filmapp.viewmodels


import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmapp.models.home.ResponseGenres
import com.example.filmapp.models.home.ResponseLastMovies
import com.example.filmapp.models.home.ResponseMovieLists
import com.example.filmapp.repository.HomeRepository
import com.example.filmapp.utils.InternetCheckClass
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModels @Inject constructor(private val repository: HomeRepository) : ViewModel() {


    val homeTopMovieLiveData = MutableLiveData<ResponseMovieLists>()
    val homeGenresLiveData = MutableLiveData<ResponseGenres>()
    val homeLastMoviesLiveData = MutableLiveData<ResponseLastMovies>()

    val progressLiveData = MutableLiveData<Boolean>()


    fun homeTopMovieLists(id: Int) = viewModelScope.launch {
        progressLiveData.postValue(true)
        val response = repository.homeTopMoviesList(id)
        if (response.isSuccessful) {
            if (response.body()?.data!!.isNotEmpty())
                homeTopMovieLiveData.postValue(response.body())
        }
        progressLiveData.postValue(false)
    }

    fun homeGenresList() {
        viewModelScope.launch {
            val response = repository.homeGenresMoviesList()

            if (response.isSuccessful) {
                if (response.body()!!.isNotEmpty())
                    homeGenresLiveData.postValue(response.body())
            }
        }
    }

    fun homeLastMoves(page: Int) {

        viewModelScope.launch {

            val response = repository.homeLastMoviesList(page)

            if (response.isSuccessful) {
                if (response.body()?.data!!.isNotEmpty())
                    homeLastMoviesLiveData.postValue(response.body())
            }

        }
    }
}