package com.madura.movieapp.domain.use_case.get_local_movie_by_id

import android.util.Log
import com.madura.movieapp.common.Resource
import com.madura.movieapp.data.dto.movieDbDto.FavoriteMovieDto
import com.madura.movieapp.data.repository.MovieLocalRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import javax.inject.Inject

class GetLocalMovieByIdUseCase @Inject constructor
    (private val movieLocalRepositoryImpl: MovieLocalRepositoryImpl) {
    val TAG = "GetLocalMovieByIdUseCase"

    operator fun invoke(movieId: Int): Flow<Resource<FavoriteMovieDto>> =
        flow {
            Log.d(TAG, "Update from favorite movie invoke: $movieId")
            try {
                emit(Resource.Loading<FavoriteMovieDto>())
                val result =
                    movieLocalRepositoryImpl.getMovieById(
                        movieId = movieId,
                    )
                Log.d(TAG, "Update from favorite movie invoke result: $result")
                if (result == null) {
                    emit(Resource.Error<FavoriteMovieDto>(message = "Movie not found"))
                } else {
                    emit(Resource.Success<FavoriteMovieDto>(data = result))
                }

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