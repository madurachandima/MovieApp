package com.madura.movieapp.domain.use_case.get_movie_suggestions

import android.util.Log
import com.madura.movieapp.common.Resource
import com.madura.movieapp.data.dto.movieListDto.MovieListDto
import com.madura.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMovieSuggestionsUseCase @Inject constructor(
    private val repository: MovieRepository,
) {
    val TAG = "GetMovieSuggestionsUseCase"

    operator fun invoke(movieId: Int): Flow<Resource<MovieListDto>> = flow {
        try {
            Log.d(
                TAG,
                "get movie suggestions request - >   movieId - $movieId"
            )
            emit(Resource.Loading<MovieListDto>())
            val movies =
                repository.getMovieSuggestionsById(movieId = movieId)
            Log.d(TAG, "get movie suggestions response - >  ${movies.toString()}")
            emit(Resource.Success<MovieListDto>(movies))
        } catch (e: HttpException) {
            Log.e(TAG, "get movie suggestions error ->>>> ${e.printStackTrace()} ")
            emit(
                Resource.Error<MovieListDto>(
                    e.localizedMessage ?: "An unexpected error occurred"
                )
            )
        } catch (e: IOException) {
            Log.e(TAG, "get movie suggestions error ->>>> ${e.printStackTrace()} ")
            emit(
                Resource.Error<MovieListDto>(
                    e.localizedMessage
                        ?: "Couldn't reach server. Check your internet connection"
                )
            )
        }
    }
}