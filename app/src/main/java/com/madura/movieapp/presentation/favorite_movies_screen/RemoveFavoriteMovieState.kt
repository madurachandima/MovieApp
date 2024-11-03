package com.madura.movieapp.presentation.favorite_movies_screen

data class RemoveFavoriteMovieState(
    val isLoading: Boolean = false,
    val movieId: Int? = null,
    val error: String = "",
)