package com.madura.movieapp.domain.use_case.get_movies

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
    operator fun invoke(page: Int?, query: String?): Flow<Resource<MovieListDto>> = flow {
        try {
            emit(Resource.Loading<MovieListDto>())
            val coin = repository.getMovies(page = page, query = query)
            emit(Resource.Success<MovieListDto>(coin))
        } catch (e: HttpException) {
            emit(Resource.Error<MovieListDto>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(
                Resource.Error<MovieListDto>(
                    e.localizedMessage ?: "Couldn't reach server. Check your internet connection"
                )
            )
        }
    }
}