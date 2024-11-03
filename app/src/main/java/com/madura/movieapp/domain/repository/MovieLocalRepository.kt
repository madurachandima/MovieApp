package com.madura.movieapp.domain.repository

import com.madura.movieapp.data.dto.movieDbDto.FavoriteMovieDto
import kotlinx.coroutines.flow.Flow

interface MovieLocalRepository {
    suspend fun addFavoriteMovie(favoriteMovieDto: FavoriteMovieDto): Long

    suspend fun removeFavoriteMovie(movieId: Int): Int

    fun getAllFavoriteMovie(): Flow<List<FavoriteMovieDto>>

}