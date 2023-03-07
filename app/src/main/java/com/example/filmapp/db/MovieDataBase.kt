package com.example.filmapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.filmapp.utils.Constants

@Database(entities = [MoviesEntity::class], version = Constants.DATABASE_VERSION, exportSchema = false)
abstract class MovieDataBase: RoomDatabase() {
 abstract fun dao(): MovieDao
}