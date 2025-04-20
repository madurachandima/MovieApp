package com.madura.movieapp.data.remote

import com.madura.movieapp.data.dto.movieDetailsDto.MovieDetailsDto
import com.madura.movieapp.data.dto.movieListDto.MovieListDto
import com.madura.movieapp.data.dto.tmdb.genre.Genres
import com.madura.movieapp.data.dto.tmdb.mediaByGenre.MediaByGenre
import com.madura.movieapp.data.dto.tmdb.trending.Trending
import retrofit2.http.GET
import retrofit2.http.Path
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

    @GET("movie_suggestions.json")
    suspend fun getMovieSuggestionsById(
        @Query("movie_id") movieId: Int,
    ): MovieListDto


    @GET("trending/all/week")
    suspend fun getPopularMoviesAndTvShowsByWeek(
    ): Trending

    @GET("genre/{mediaType}/list")
    suspend fun getGenre(
        @Path("mediaType") genre: String,
    ): Genres

    @GET("discover/{mediaType}")
    suspend fun getMovieOrTvSeriesByGenre(
        @Path("mediaType") mediaType: String,
        @Query("with_genres") genreId: String,
        @Query("page") page: Int,
    ): MediaByGenre
}