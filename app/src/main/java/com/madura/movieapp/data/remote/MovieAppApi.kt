package com.madura.movieapp.data.remote

import com.madura.movieapp.data.dto.MovieListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAppApi {
    @GET("list_movies.json")
    suspend fun getMovieList(
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Query("query_term") query: String,
        ) : MovieListDto
}