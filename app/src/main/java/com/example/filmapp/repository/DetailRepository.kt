package com.example.filmapp.repository

import com.example.filmapp.api.ApiServices
import com.example.filmapp.db.MovieDao
import com.example.filmapp.db.MoviesEntity
import javax.inject.Inject

class DetailRepository @Inject constructor(private val api: ApiServices, private val dao: MovieDao) {

    //api
    suspend fun detailMovies(id: Int) = api.detailMovies(id)

    //database
    suspend fun insertData(entity: MoviesEntity) = dao.insertMovie(entity)
    suspend fun deleteData(entity: MoviesEntity) = dao.deleteMovie(entity)
    suspend fun existData(id: Int) = dao.existMove(id)

}