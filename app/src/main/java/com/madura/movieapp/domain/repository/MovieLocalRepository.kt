package com.madura.movieapp.domain.repository

import com.madura.movieapp.data.dto.movieDbDto.FavoriteMovieDto
import kotlinx.coroutines.flow.Flow

interface MovieLocalRepository {
    suspend fun insertAndReturnMovie(favoriteMovieDto: FavoriteMovieDto): FavoriteMovieDto?

    suspend fun removeMovieFromDb(movieId: Int): Int

    suspend fun getAllLocalMovies(): Flow<List<FavoriteMovieDto>>

    suspend fun updateFavoriteStatusAndReturnMovie(
        movieId: Int,
        isFavorite: Boolean
    ): FavoriteMovieDto

    suspend fun updateWatchListStatus(movieId: Int, isWatched: Boolean): Int

    suspend fun getMovieById(movieId: Int): FavoriteMovieDto?

}