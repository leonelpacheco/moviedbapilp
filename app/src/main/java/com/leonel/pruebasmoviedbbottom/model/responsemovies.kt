package com.leonel.pruebasmoviedbbottom.model

import com.google.gson.annotations.SerializedName

data class responsemovies(
    @SerializedName("page") val page: String,
    @SerializedName("total_pages") val total_pages : String,
    @SerializedName("total_results") val total_results: String,
    @SerializedName("results") val results: List<movie>
)
