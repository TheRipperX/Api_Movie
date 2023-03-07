package com.example.filmapp.di

import android.content.Context
import androidx.room.Room
import com.example.filmapp.db.MovieDataBase
import com.example.filmapp.db.MoviesEntity
import com.example.filmapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun proDataBase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        MovieDataBase::class.java,
        Constants.DATABASE_MOVIES
    )
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun proDao(db: MovieDataBase) = db.dao()

    @Provides
    @Singleton
    fun proEntity() = MoviesEntity()
}