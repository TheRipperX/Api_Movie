package com.example.filmapp.models.home


import com.google.gson.annotations.SerializedName

class ResponseGenres : ArrayList<ResponseGenres.GenresHomeMovieItem>(){
    data class GenresHomeMovieItem(
        @SerializedName("id")
        val id: Int?, // 1
        @SerializedName("name")
        val name: String? // Crime
    )
}