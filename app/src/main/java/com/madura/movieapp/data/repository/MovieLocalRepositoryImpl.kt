package com.madura.movieapp.data.repository


import com.madura.movieapp.data.dao.MovieDbDao
import com.madura.movieapp.data.dto.movieDbDto.FavoriteMovieDto
import com.madura.movieapp.domain.repository.MovieLocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class MovieLocalRepositoryImpl @Inject constructor(
    private val dao: MovieDbDao,
) : MovieLocalRepository {

    override suspend fun addFavoriteMovie(favoriteMovieDto: FavoriteMovieDto): Long {
        return dao.addFavoriteMovie(favoriteMovieDto)
    }

    override suspend fun removeFavoriteMovie(movieId: Int): Int {
        return dao.removeFavoriteMovie(movieId)
    }

    override fun getAllFavoriteMovie(): Flow<List<FavoriteMovieDto>> {
        return dao.getAllFavoriteMovie()
    }
}