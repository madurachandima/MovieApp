package com.madura.movieapp.data.repository

import com.madura.movieapp.data.dto.movieDetailsDto.MovieDetailsDto
import com.madura.movieapp.data.dto.movieListDto.MovieListDto
import com.madura.movieapp.data.remote.MovieAppApi
import com.madura.movieapp.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieAppApi,
) : MovieRepository {
    override suspend fun getMovies(
        query: String?,
        page: Int?,
        sortBy: String?,
        genre: String?,

        ): MovieListDto {
        return api.getMovieList(
            limit = 20,
            query = query ?: "",
            page = page ?: 1,
            sortBy = sortBy ?: "date_added",
            genre = genre
        )
    }

    override suspend fun getMovieDetailsById(
        movieId: Int,
        withImage: Boolean?,
        withCast: Boolean?,
    ): MovieDetailsDto {
        return api.getMovieDetailById(
            movieId = movieId,
            withImage = withImage ?: true,
            withCast = withCast ?: true
        )
    }

    override suspend fun getMovieSuggestionsById(movieId: Int): MovieListDto {
        return api.getMovieSuggestionsById(
            movieId = movieId,
            )
    }
}