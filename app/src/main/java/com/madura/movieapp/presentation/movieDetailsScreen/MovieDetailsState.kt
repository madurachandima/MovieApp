package com.madura.movieapp.presentation.movieDetailsScreen

import com.madura.movieapp.data.dto.movieDetailsDto.Movie


data class MovieDetailsState(
    val id: Long? = null,
    val isLoading: Boolean = false,
    val movieDetails: Movie? = null,
    val error: String = "",
)

//data class InsertToFavoriteState(
//    val isLoading: Boolean = false,
//    val id: Long? = null,
//    val error: String = "",
//)


