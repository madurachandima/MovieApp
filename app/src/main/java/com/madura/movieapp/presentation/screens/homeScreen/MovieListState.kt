package com.madura.movieapp.presentation.screens.homeScreen

import com.madura.movieapp.data.dto.MovieListDto

data class MovieListState(
    val isLoading: Boolean = false,
    val movies: MovieListDto? = null,
    val error: String = "",
)