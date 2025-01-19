package com.madura.movieapp.presentation.favorite_movies_screen

import com.madura.movieapp.data.dto.movieDbDto.FavoriteMovieDto

data class MovieState(
    val isLoading: Boolean = false,
    val movieId: Int? = null,
    val error: String = "",
    val movie: FavoriteMovieDto? = null

)