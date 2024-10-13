package com.madura.movieapp.domain.use_case.insert_to_favorite_movie


import android.util.Log
import com.madura.movieapp.common.Resource
import com.madura.movieapp.data.dto.movieDbDto.FavoriteMovieDto
import com.madura.movieapp.data.repository.MovieLocalRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InsertToFavoriteMovieUseCase @Inject constructor(
    val movieLocalRepositoryImpl: MovieLocalRepositoryImpl,
) {
    val TAG = "InsertToFavoriteMovieUseCase"

     operator fun invoke(movie: FavoriteMovieDto): Flow<Resource<Long>> = flow {
        try {
            emit(Resource.Loading<Long>())
            val result = movieLocalRepositoryImpl.addFavoriteMovie(favoriteMovieDto = movie)
            Log.d(TAG, "Insert to favorite movie invoke: $result")
            emit(Resource.Success<Long>(result))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(
                Resource.Error<Long>(
                    e.localizedMessage ?: "An unexpected error occurred"
                )
            )
        }
    }

}