package com.madura.movieapp.data.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.madura.movieapp.data.dto.movieDbDto.FavoriteMovieDto
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDbDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovieToDb(favoriteMovieDto: FavoriteMovieDto): Long

    @Query("SELECT * FROM FavoriteMovieDto WHERE isFavorite = 1")
    fun getAllDbMovie(): Flow<List<FavoriteMovieDto>>

    @Query("DELETE FROM FavoriteMovieDto WHERE movieId = :movieId")
    suspend fun removeMovieFromDb(movieId: Int): Int

    @Query("UPDATE FavoriteMovieDto SET isFavorite = :isFavorite WHERE movieId = :movieId")
    suspend fun updateFavoriteStatus(movieId: Int, isFavorite: Boolean): Int

    @Query("UPDATE FavoriteMovieDto SET isWatched = :isWatched WHERE movieId = :movieId")
    suspend fun updateWatchListStatus(movieId: Int, isWatched: Boolean): Int

    @Query("SELECT * FROM FavoriteMovieDto WHERE movieId = :movieId")
    suspend fun getMovieById(movieId: Int): FavoriteMovieDto?

    @Transaction
    suspend fun updateFavoriteStatusAndReturnMovie(
        movieId: Int,
        isFavorite: Boolean
    ): FavoriteMovieDto {
        updateFavoriteStatus(movieId = movieId, isFavorite = isFavorite)
        return getMovieById(movieId = movieId) ?: FavoriteMovieDto()
    }

    @Transaction
    suspend fun insertAndReturnMovie(favoriteMovieDto: FavoriteMovieDto): FavoriteMovieDto? {
        addMovieToDb(favoriteMovieDto = favoriteMovieDto)
        return getMovieById(movieId = favoriteMovieDto.movieId)
    }
}