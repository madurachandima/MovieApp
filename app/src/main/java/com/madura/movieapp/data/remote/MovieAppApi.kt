package com.madura.movieapp.data.remote

import com.madura.movieapp.data.dto.movieDetailsDto.MovieDetailsDto
import com.madura.movieapp.data.dto.movieListDto.MovieListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAppApi {
    @GET("list_movies.json")
    suspend fun getMovieList(
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Query("query_term") query: String,
        @Query("sort_by") sortBy: String,
        @Query("genre") genre: String?,
    ): MovieListDto

    @GET("movie_details.json")
    suspend fun getMovieDetailById(
        @Query("movie_id") movieId: Int,
        @Query("with_images") withImage: Boolean,
        @Query("with_cast") withCast: Boolean,
    ): MovieDetailsDto


}