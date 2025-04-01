package com.madura.movieapp.domain.use_case.get_movies

import android.util.Log
import com.madura.movieapp.common.Resource
import com.madura.movieapp.data.dto.movieListDto.MovieListDto
import com.madura.movieapp.data.dto.tmdb.trending.Trending
import com.madura.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMoveUseCase @Inject constructor(
    private val repository: MovieRepository,
) {
    val TAG = "GetMoveUseCase"
    operator fun invoke(
    ): Flow<Resource<Trending>> =
        flow {
            try {
                emit(Resource.Loading<Trending>())
                val movies =
                    repository.getPopularMoviesAndTvShowsByWeek()
                Log.d(TAG, "get movies response - >  ${movies.toString()}")

                emit(Resource.Success<Trending>(movies))
            } catch (e: HttpException) {
                Log.e(TAG, "get movies error ->>>> ${e.printStackTrace()} ")
                emit(
                    Resource.Error<Trending>(
                        e.localizedMessage ?: "An unexpected error occurred"
                    )
                )
            } catch (e: IOException) {
                Log.e(TAG, "get movies error ->>>> ${e.printStackTrace()} ")
                emit(
                    Resource.Error<Trending>(
                        e.localizedMessage
                            ?: "Couldn't reach server. Check your internet connection"
                    )
                )
            }
        }
}