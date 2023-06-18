package com.leonel.pruebasmoviedbbottom.network

import com.leonel.pruebasmoviedbbottom.model.responsemovies
import com.leonel.pruebasmoviedbbottom.utils.Constant
import retrofit2.Response
import retrofit2.http.GET

interface movieApiClient {
    @GET(Constant.QUERY_MOVIEDB + Constant.KEY_MOVIEBD)
    suspend fun getAllmovies(): Response<responsemovies>

    @GET(Constant.QUERY_MOVIERATE + Constant.KEY_MOVIEBD)
    suspend fun getAllmoviesRate(): Response<responsemovies>

    @GET(Constant.QUERY_MOVIETRENDING + Constant.KEY_MOVIEBD)
    suspend fun getAllmoviesTrending(): Response<responsemovies>
}