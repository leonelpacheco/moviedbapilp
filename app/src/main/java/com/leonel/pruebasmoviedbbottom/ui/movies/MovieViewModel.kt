package com.leonel.pruebasmoviedbbottom.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leonel.pruebasmoviedbbottom.database.entities.toDatabase
import com.leonel.pruebasmoviedbbottom.model.movie
import com.leonel.pruebasmoviedbbottom.repository.firestoreRepository
import com.leonel.pruebasmoviedbbottom.repository.movieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val repository: movieRepository): ViewModel() {

    val listmovieModel = MutableLiveData<List<movie>>()
    val listmovieRateModel = MutableLiveData<List<movie>>()
    val listmovieTrendingModel = MutableLiveData<List<movie>>()
    val isLoading = MutableLiveData<Boolean>()

    fun onCreate(){
        viewModelScope.launch {
            isLoading.postValue(true)
            val result= invoke()
            if (!result.isNullOrEmpty()) {
                listmovieModel.postValue(result)

                isLoading.postValue(false)
            }
            else
                isLoading.postValue(false)

        }
    }

    fun getMoviesRate(){
        viewModelScope.launch {
            isLoading.postValue(true)
            val result= invokeRate()
            if (!result.isNullOrEmpty()) {
                listmovieRateModel.postValue(result)
                isLoading.postValue(false)
            }
            else
                isLoading.postValue(false)

        }
    }

    fun getMoviesTrending(){
        viewModelScope.launch {
            isLoading.postValue(true)
            val result= invokeTrending()
            if (!result.isNullOrEmpty()) {
                listmovieTrendingModel.postValue(result)
                isLoading.postValue(false)
            }
            else
                isLoading.postValue(false)

        }
    }


    //*************llamado al repository********

    suspend operator fun invoke():List<movie>{
        val movies = repository.getAllMoviesFromApi()

        return if(movies.isNotEmpty()){
            repository.clearMovies()
            repository.insertMovies(movies.map { it.toDatabase() })
            movies
        }else{
            repository.getAllMoviesFromDatabase()
        }
    }

    suspend fun invokeRate():List<movie>{
        val movies = repository.getAllMoviesFromApiRate()

        return if(movies.isNotEmpty()){
            repository.clearMovies()
            repository.insertMovies(movies.map { it.toDatabase() })
            movies
        }else{
            repository.getAllMoviesFromDatabase()
        }
    }

    suspend fun invokeTrending():List<movie>{
        val movies = repository.getAllMoviesFromApiTrending()

        return if(movies.isNotEmpty()){
            repository.clearMovies()
            repository.insertMovies(movies.map { it.toDatabase() })
            movies
        }else{
            repository.getAllMoviesFromDatabase()
        }
    }

    //*******************************************

}