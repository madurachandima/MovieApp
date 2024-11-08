package com.madura.movieapp.presentation.favorite_movies_screen

import com.madura.movieapp.data.dto.movieDbDto.FavoriteMovieDto


data class FavoriteMovieListState(
    val isLoading: Boolean = false,
    val isPaginationLoading: Boolean = false,
    val movies: List<FavoriteMovieDto>? = null,
    val error: String = "",
)