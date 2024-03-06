package com.madura.movieapp.domain.use_case.get_movieDetails

import android.util.Log
import com.madura.movieapp.common.Resource
import com.madura.movieapp.data.dto.movieDetailsDto.MovieDetailsDto
import com.madura.movieapp.data.dto.movieListDto.MovieListDto
import com.madura.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class GetMovieDetailsUseCase @Inject constructor(
    private val repository: MovieRepository,
) {

    val TAG = "GetMovieDetailsUseCase"

    operator fun invoke(movieId: Int): Flow<Resource<MovieDetailsDto>> = flow {
        try {
            emit(Resource.Loading<MovieDetailsDto>())
            val movieDetails =
                repository.getMovieDetailsById(movieId = movieId, withImage = true, withCast = true)

            emit(Resource.Success<MovieDetailsDto>(movieDetails))

        } catch (e: HttpException) {
            Log.e(TAG, "get movie details error ->>>> ${e.printStackTrace()} ")
            emit(
                Resource.Error<MovieDetailsDto>(
                    e.localizedMessage ?: "An unexpected error occurred"
                )
            )
        } catch (e: IOException) {
            Log.e(TAG, "get movie details error ->>>> ${e.printStackTrace()} ")
            emit(
                Resource.Error<MovieDetailsDto>(
                    e.localizedMessage
                        ?: "Couldn't reach server. Check your internet connection"
                )
            )
        }
    }
}