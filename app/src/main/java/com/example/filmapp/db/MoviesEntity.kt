package com.example.filmapp.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.filmapp.utils.Constants

@Entity(tableName = Constants.TABLE_MOVIES)
data class MoviesEntity(

    @PrimaryKey
    var id: Int = 0,
    var imgMovie: String = "",
    var titleMovie: String = "",
    var starsMovie: String = "",
    var countryMovie: String = "",
    var yearMovie: String = ""

)
