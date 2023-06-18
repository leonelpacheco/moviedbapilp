package com.leonel.pruebasmoviedbbottom.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.leonel.pruebasmoviedbbottom.database.dao.movieDao
import com.leonel.pruebasmoviedbbottom.database.entities.movieEntity


@Database(entities = [movieEntity::class], version = 1)
abstract class MovieDatabase: RoomDatabase() {

    abstract fun getMovieDao(): movieDao

}