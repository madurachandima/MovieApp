package com.madura.movieapp.domain.use_case.get_movie_genre

import android.util.Log
import com.madura.movieapp.common.Resource
import com.madura.movieapp.data.dto.tmdb.genre.Genres
import com.madura.movieapp.data.repository.MovieRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetMovieGenreUseCase @Inject constructor(
    private val movieRepositoryImpl: MovieRepositoryImpl
) {
    val TAG = "GetMovieGenreUseCase"

    operator fun invoke(genre: String): Flow<Resource<Genres>> = flow {
        try {
            emit(Resource.Loading<Genres>())
            val result = movieRepositoryImpl.getGenre(genre = genre)
            Log.d(TAG, "GetMovieGenreUseCase invoke result: $result")
            emit(Resource.Success<Genres>(data = result))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(
                Resource.Error<Genres>(
                    message = e.message ?: "An unexpected error occurred"
                )
            )

        }
    }
}