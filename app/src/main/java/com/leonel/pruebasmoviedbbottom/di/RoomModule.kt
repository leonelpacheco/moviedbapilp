package com.leonel.pruebasmoviedbbottom.di

import android.content.Context
import androidx.room.Room
import com.leonel.pruebasmoviedbbottom.database.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    private const val Movie_DATABASE_NAME = "movie_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, MovieDatabase::class.java, Movie_DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideQuoteDao(db: MovieDatabase) = db.getMovieDao()
}