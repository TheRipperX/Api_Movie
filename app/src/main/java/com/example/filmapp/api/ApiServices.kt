package com.example.filmapp.api

import com.example.filmapp.models.detail.ResponseDetails
import com.example.filmapp.models.home.ResponseGenres
import com.example.filmapp.models.home.ResponseLastMovies
import com.example.filmapp.models.home.ResponseMovieLists
import com.example.filmapp.models.register.BodyRegister
import com.example.filmapp.models.register.ResponseRegister
import com.example.filmapp.models.search.ResponseSearch
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiServices {

    @POST("register")
    suspend fun registerUser(@Body bodyRegister: BodyRegister): Response<ResponseRegister>

    @GET("genres/{genre_id}/movies")
    suspend fun homeTopMovieList(@Path("genre_id") id: Int): Response<ResponseMovieLists>

    @GET("genres")
    suspend fun genresMovies(): Response<ResponseGenres>

    @GET("movies")
    suspend fun lastMovies(@Query("page") page: Int): Response<ResponseLastMovies>

    @GET("movies")
    suspend fun searchMovies(@Query("q") name: String): Response<ResponseSearch>

    @GET("movies/{movie_id}")
    suspend fun detailMovies(@Path("movie_id") id: Int): Response<ResponseDetails>

}