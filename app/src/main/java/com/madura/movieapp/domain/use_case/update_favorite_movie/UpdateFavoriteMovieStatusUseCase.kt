package com.madura.movieapp.domain.use_case.update_favorite_movie

import android.util.Log
import com.madura.movieapp.common.Resource
import com.madura.movieapp.data.dto.movieDbDto.FavoriteMovieDto
import com.madura.movieapp.data.repository.MovieLocalRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateFavoriteMovieStatusUseCase @Inject constructor
    (private val movieLocalRepositoryImpl: MovieLocalRepositoryImpl) {
    val TAG = "UpdateFavoriteMovieStatusUseCase"

    operator fun invoke(movieId: Int, isFavorite: Boolean): Flow<Resource<FavoriteMovieDto>> =
        flow {
            Log.d(TAG, "Update from favorite movie invoke: $movieId")
            try {
                emit(Resource.Loading<FavoriteMovieDto>())
                val result =
                    movieLocalRepositoryImpl.updateFavoriteStatusAndReturnMovie(
                        movieId = movieId,
                        isFavorite = isFavorite
                    )
                Log.d(TAG, "Update from favorite movie invoke result: $result")
                emit(Resource.Success<FavoriteMovieDto>(data = result))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(
                    Resource.Error<FavoriteMovieDto>(
                        message = e.message ?: "An unexpected error occurred"
                    )
                )

            }
        }


}

