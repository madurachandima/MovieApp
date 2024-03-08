package com.madura.movieapp.domain.repository

import com.madura.movieapp.data.dto.movieDetailsDto.MovieDetailsDto
import com.madura.movieapp.data.dto.movieListDto.MovieListDto

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
}