package com.madura.movieapp.data.repository


import com.madura.movieapp.data.dao.MovieDbDao
import com.madura.movieapp.data.dto.movieDbDto.FavoriteMovieDto
import com.madura.movieapp.domain.repository.MovieLocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class MovieLocalRepositoryImpl @Inject constructor(
    private val dao: MovieDbDao,
) : MovieLocalRepository {

    override suspend fun insertAndReturnMovie(favoriteMovieDto: FavoriteMovieDto)
            : FavoriteMovieDto? {
        return dao.insertAndReturnMovie(favoriteMovieDto)

    }

    override suspend fun removeMovieFromDb(movieId: Int): Int {
        return dao.removeMovieFromDb(movieId)
    }

    override suspend fun getAllLocalMovies(): Flow<List<FavoriteMovieDto>> {
        return dao.getAllDbMovie()
    }

    override suspend fun updateFavoriteStatusAndReturnMovie(
        movieId: Int,
        isFavorite: Boolean
    ): FavoriteMovieDto {
        return dao.updateFavoriteStatusAndReturnMovie(movieId, isFavorite)
    }

    override suspend fun updateWatchListStatus(movieId: Int, isWatched: Boolean): Int {
        return dao.updateWatchListStatus(movieId, isWatched)
    }

    override suspend fun getMovieById(movieId: Int): FavoriteMovieDto? {
        return dao.getMovieById(movieId)
    }
}