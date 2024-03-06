package com.madura.movieapp.presentation.movieDetailsScreen

import com.madura.movieapp.data.dto.movieDetailsDto.Movie


data class MovieDetailsState(
    val isLoading: Boolean = false,
    val movieDetails: Movie? = null,
    val error: String = "",
)
