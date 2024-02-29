package com.madura.movieapp.domain.use_case.get_movies

import android.util.Log
import com.madura.movieapp.common.Resource
import com.madura.movieapp.data.dto.MovieListDto
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
        page: Int?,
        query: String?,
        sortBy: String?,
        genre: String?,
    ): Flow<Resource<MovieListDto>> =
        flow {
            try {
                Log.d(
                    TAG,
                    "get movies request - >   page - $page query - $query sortBy - $sortBy genre - $genre"
                )
                emit(Resource.Loading<MovieListDto>())
                val movies =
                    repository.getMovies(page = page, query = query, sortBy = sortBy, genre = genre)
                Log.d(TAG, "get movies response - >  ${movies.toString()}")

                emit(Resource.Success<MovieListDto>(movies))
            } catch (e: HttpException) {
                Log.e(TAG, "get movies error ->>>> ${e.printStackTrace()} ")
                emit(
                    Resource.Error<MovieListDto>(
                        e.localizedMessage ?: "An unexpected error occurred"
                    )
                )
            } catch (e: IOException) {
                Log.e(TAG, "get movies error ->>>> ${e.printStackTrace()} ")
                emit(
                    Resource.Error<MovieListDto>(
                        e.localizedMessage
                            ?: "Couldn't reach server. Check your internet connection"
                    )
                )
            }
        }
}