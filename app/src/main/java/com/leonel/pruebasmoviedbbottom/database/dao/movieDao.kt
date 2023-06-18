package com.leonel.pruebasmoviedbbottom.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.leonel.pruebasmoviedbbottom.database.entities.movieEntity

@Dao
interface movieDao {
    @Query("SELECT * FROM movie_table ORDER BY id DESC")
    suspend fun getAllMovies():List<movieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies:List<movieEntity>)

    @Query("DELETE FROM movie_table")
    suspend fun deleteAllMovies()

}