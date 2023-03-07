package com.example.filmapp.repository

import com.example.filmapp.api.ApiServices
import javax.inject.Inject

class SearchRepository @Inject constructor(private val api: ApiServices) {

    suspend fun searchMovies(name: String) = api.searchMovies(name)
}