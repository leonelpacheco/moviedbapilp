package com.leonel.pruebasmoviedbbottom.repository

import com.leonel.pruebasmoviedbbottom.database.dao.movieDao
import com.leonel.pruebasmoviedbbottom.database.entities.movieEntity
import com.leonel.pruebasmoviedbbottom.model.movie
import com.leonel.pruebasmoviedbbottom.model.toDomain
import com.leonel.pruebasmoviedbbottom.network.movieService
import javax.inject.Inject

class movieRepository @Inject constructor(private val api: movieService,
                                          private val movieDao: movieDao
){


    //*****************
    suspend fun getAllMoviesFromApi(): List<movie> {
        val response: List<movie> = api.getMovies()
        return response.map { it.toDomain() }
    }

    suspend fun getAllMoviesFromApiRate(): List<movie> {
        val response: List<movie> = api.getMoviesRate()
        return response.map { it.toDomain() }
    }

    suspend fun getAllMoviesFromApiTrending(): List<movie> {
        val response: List<movie> = api.getMoviesTrending()
        return response.map { it.toDomain() }
    }

    suspend fun getAllMoviesFromDatabase():List<movie>{
        val response: List<movieEntity> = movieDao.getAllMovies()
        return response.map { it.toDomain() }
    }
    //**********************
    suspend fun insertMovies(movies:List<movieEntity>){
        movieDao.insertAll(movies)
    }

    suspend fun clearMovies(){
        movieDao.deleteAllMovies()
    }

}