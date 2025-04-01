package com.madura.movieapp.presentation.homeScreen

import com.madura.movieapp.data.dto.movieListDto.Movie
import com.madura.movieapp.data.dto.tmdb.genre.Genres
import com.madura.movieapp.data.dto.tmdb.mediaByGenre.Result
import com.madura.movieapp.data.dto.tmdb.trending.TrendingResult


data class PopularMovieListState(
    val isLoading: Boolean = false,
    val isPaginationLoading: Boolean = false,
    val sortedMovies: ArrayList<TrendingResult> = arrayListOf(),
    val error: String? = "",
)

data class MovieListState(
    val isLoading: Boolean = false,
    val isPaginationLoading: Boolean = false,
    val movies: ArrayList<Movie>? = null,
    val error: String = "",
)

data class MediaByGenreState(
    val isLoading: Boolean = false,
    val isPaginationLoading: Boolean = false,
    val movies: ArrayList<Result?>? = null,
    val error: String = "",
)

data class HomeMovieGenreState(
    val isLoading: Boolean = false,
    val genres: Genres? = null,
    val error: String = "",
)