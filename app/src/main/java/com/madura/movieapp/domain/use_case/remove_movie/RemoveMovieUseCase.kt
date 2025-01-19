package com.madura.movieapp.domain.use_case.remove_movie

import android.util.Log
import com.madura.movieapp.common.Resource
import com.madura.movieapp.data.repository.MovieLocalRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoveMovieUseCase @Inject constructor(
    private val movieLocalRepositoryImpl: MovieLocalRepositoryImpl
) {
    val TAG = "RemoveFavoriteMovieUseCase"

    operator fun invoke(movieId: Int): Flow<Resource<Int>> = flow {
        Log.d(TAG, "Remove from favorite movie invoke: $movieId")
        try {
            emit(Resource.Loading<Int>())
            val result = movieLocalRepositoryImpl.removeMovieFromDb(movieId = movieId)
            Log.d(TAG, "Remove from favorite movie invoke result: $result")
            emit(Resource.Success<Int>(data = result))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(
                Resource.Error<Int>(
                    message = e.message ?: "An unexpected error occurred"
                )
            )

        }
    }
}