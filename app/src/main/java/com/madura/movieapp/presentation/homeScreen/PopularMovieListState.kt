package com.madura.movieapp.presentation.homeScreen

import com.madura.movieapp.data.dto.movieListDto.Movie

data class PopularMovieListState(
    val isLoading: Boolean = false,
    val isPaginationLoading: Boolean = false,
    val sortedMovies: ArrayList<Movie> = arrayListOf(),
    val error: String = "",
)