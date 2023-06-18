package com.leonel.pruebasmoviedbbottom.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.leonel.pruebasmoviedbbottom.model.movie

@Entity(tableName = "movie_table")
data class movieEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "poster_path") val poster_path: String,
    @ColumnInfo(name = "vote_average") val vote_average: String,
    @ColumnInfo(name = "release_date") val release_date: String,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "popularity") val popularity: String,
    @ColumnInfo(name = "original_languaje") val original_language: String
)

fun movie.toDatabase()=movieEntity(title = title,poster_path = poster_path,vote_average = vote_average,release_date = release_date,
    overview = overview,popularity = popularity,original_language = original_language)
