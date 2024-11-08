package com.madura.movieapp.domain.use_case.get_favorite_movies

import android.util.Log
import com.madura.movieapp.common.Resource
import com.madura.movieapp.data.dto.movieDbDto.FavoriteMovieDto
import com.madura.movieapp.data.repository.MovieLocalRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFavoriteMovieUseCase @Inject constructor(
    private val movieLocalRepositoryImpl: MovieLocalRepositoryImpl,
) {
    val TAG = "GetFavoriteMovieUseCase"

    operator fun invoke(): Flow<Resource<List<FavoriteMovieDto>>> = flow {
        try {
            emit(Resource.Loading<List<FavoriteMovieDto>>())
            movieLocalRepositoryImpl.getAllFavoriteMovie().collect { favoriteMovies ->
                emit(Resource.Success<List<FavoriteMovieDto>>(favoriteMovies))
                Log.d(TAG, "Get all favorite movie invoke favoriteMovies : ${favoriteMovies.size}")
            }

        } catch (e: Exception) {
            e.printStackTrace()
            emit(
                Resource.Error<List<FavoriteMovieDto>>(
                    e.localizedMessage ?: "An unexpected error occurred"
                )
            )
        }
    }
}