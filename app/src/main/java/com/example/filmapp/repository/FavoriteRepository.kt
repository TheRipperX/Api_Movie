package com.example.filmapp.repository

import com.example.filmapp.db.MovieDao
import javax.inject.Inject

class FavoriteRepository @Inject constructor(private val dao: MovieDao) {

    fun allFavListMovie() = dao.getAllMove()
}