package com.leonel.pruebasmoviedbbottom.model

import com.leonel.pruebasmoviedbbottom.database.entities.movieEntity
import com.google.gson.annotations.SerializedName

data class movie(
    @SerializedName("title") val title: String,
    @SerializedName("poster_path") val poster_path: String,
    @SerializedName("vote_average") val vote_average: String,
    @SerializedName("release_date") val release_date: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("popularity") val popularity: String,
    @SerializedName("original_language") val original_language: String
)

fun movie.toDomain() = movie(title, poster_path,vote_average,release_date,overview,popularity,original_language)
fun movieEntity.toDomain() = movie(title, poster_path,vote_average,release_date,overview,popularity,original_language)