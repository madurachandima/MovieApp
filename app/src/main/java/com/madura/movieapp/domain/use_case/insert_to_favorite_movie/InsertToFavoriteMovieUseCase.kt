package com.madura.movieapp.domain.use_case.insert_to_favorite_movie


import android.util.Log
import com.madura.movieapp.common.Resource
import com.madura.movieapp.data.dto.movieDbDto.FavoriteMovieDto
import com.madura.movieapp.data.repository.MovieLocalRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InsertToFavoriteMovieUseCase @Inject constructor(
    private val movieLocalRepositoryImpl: MovieLocalRepositoryImpl,
) {
    val TAG = "InsertToFavoriteMovieUseCase"

    operator fun invoke(movie: FavoriteMovieDto): Flow<Resource<FavoriteMovieDto?>> = flow {
        try {
            emit(Resource.Loading<FavoriteMovieDto?>())
            val result = movieLocalRepositoryImpl.insertAndReturnMovie(favoriteMovieDto = movie)
            Log.d(TAG, "Insert to favorite movie invoke: $result")
            emit(Resource.Success<FavoriteMovieDto?>(result))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(
                Resource.Error<FavoriteMovieDto?>(
                    e.localizedMessage ?: "An unexpected error occurred"
                )
            )
        }
    }

}