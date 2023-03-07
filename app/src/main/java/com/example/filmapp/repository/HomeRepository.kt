package com.example.filmapp.repository

import com.example.filmapp.api.ApiServices
import com.example.filmapp.models.home.ResponseGenres
import retrofit2.Response
import javax.inject.Inject

class HomeRepository @Inject constructor(private val api: ApiServices) {

    suspend fun homeTopMoviesList(id: Int) = api.homeTopMovieList(id)

    suspend fun homeGenresMoviesList(): Response<ResponseGenres> {
        return api.genresMovies()
    }

    suspend fun homeLastMoviesList(page: Int) = api.lastMovies(page)
}