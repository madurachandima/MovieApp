package com.madura.movieapp.presentation.homeScreen

import com.madura.movieapp.data.dto.Movie


data class MovieListState(
    val isLoading: Boolean = false,
    val isPaginationLoading: Boolean = false,
    val movies: ArrayList<Movie>? = null,
    val error: String = "",
)