package com.example.filmapp.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.filmapp.utils.Constants

@Dao
interface MovieDao {

    //insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(entity: MoviesEntity)

    @Delete
    suspend fun deleteMovie(entity: MoviesEntity)

    @Query("SELECT * FROM ${Constants.TABLE_MOVIES}")
    fun getAllMove(): MutableList<MoviesEntity>

    @Query("SELECT EXISTS (SELECT 1 FROM ${Constants.TABLE_MOVIES} WHERE id = :movieId)")
    suspend fun existMove(movieId: Int): Boolean
}