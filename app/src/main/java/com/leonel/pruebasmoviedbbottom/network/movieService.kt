package com.leonel.pruebasmoviedbbottom.network

import com.leonel.pruebasmoviedbbottom.model.movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class movieService @Inject constructor(private val api:movieApiClient){

    suspend fun getMovies(): List<movie> {
        return withContext(Dispatchers.IO) {
            val response = api.getAllmovies()
            response.body()?.results ?: emptyList()
        }
    }

    suspend fun getMoviesRate(): List<movie> {
        return withContext(Dispatchers.IO) {
            val response = api.getAllmoviesRate()
            response.body()?.results ?: emptyList()
        }
    }

    suspend fun getMoviesTrending(): List<movie> {
        return withContext(Dispatchers.IO) {
            val response = api.getAllmoviesTrending()
            response.body()?.results ?: emptyList()
        }
    }
}