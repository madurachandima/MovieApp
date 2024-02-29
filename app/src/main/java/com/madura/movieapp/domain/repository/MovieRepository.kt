package com.madura.movieapp.domain.repository

import com.madura.movieapp.data.dto.MovieListDto

interface MovieRepository {
    suspend fun getMovies(query: String?, page: Int?, sortBy: String?, genre: String?): MovieListDto
}