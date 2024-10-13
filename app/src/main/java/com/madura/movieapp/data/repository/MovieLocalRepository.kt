package com.madura.movieapp.data.repository


import com.madura.movieapp.data.dao.MovieDbDao
import com.madura.movieapp.data.dto.movieDbDto.FavoriteMovieDto
import javax.inject.Inject



class MovieLocalRepositoryImpl @Inject constructor(
    private val dao: MovieDbDao,
)  {
    suspend fun addFavoriteMovie(favoriteMovieDto: FavoriteMovieDto) :Long{
       return dao.addFavoriteMovie(favoriteMovieDto)
    }
    fun getAllFavoriteMovie() = dao.getAllFavoriteMovie()


}