package com.madura.movieapp.domain.repository

import com.madura.movieapp.data.dto.movieDetailsDto.MovieDetailsDto
import com.madura.movieapp.data.dto.movieListDto.MovieListDto
import com.madura.movieapp.data.dto.tmdb.genre.Genres
import com.madura.movieapp.data.dto.tmdb.mediaByGenre.MediaByGenre
import com.madura.movieapp.data.dto.tmdb.trending.Trending

interface MovieRepository {
    suspend fun getMovies(query: String?, page: Int?, sortBy: String?, genre: String?): MovieListDto

    suspend fun getMovieDetailsById(
        movieId: Int,
        withImage: Boolean?,
        withCast: Boolean?,
    ): MovieDetailsDto

    suspend fun getMovieSuggestionsById(
        movieId: Int,
    ): MovieListDto

    suspend fun getPopularMoviesAndTvShowsByWeek(): Trending


    suspend fun getGenre(genre: String): Genres

    suspend fun getMovieOrTvSeriesByGenre(
        mediaType: String,
        genreId: String,
        page: Int
    ): MediaByGenre

}