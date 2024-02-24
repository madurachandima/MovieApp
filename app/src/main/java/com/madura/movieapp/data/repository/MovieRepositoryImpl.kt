package com.madura.movieapp.data.repository

import android.util.Log
import com.madura.movieapp.data.dto.MovieListDto
import com.madura.movieapp.data.remote.MovieAppApi
import com.madura.movieapp.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieAppApi,
) : MovieRepository {
    override suspend fun getMovies(query: String?, page: Int?): MovieListDto {
        Log.d("MovieRepositoryImpl","page number ------>> $page")
        return api.getMovieList(limit = 20, query = query ?: "", page = page ?: 1)
    }
}