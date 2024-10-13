package com.madura.movieapp.data.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.madura.movieapp.data.dto.movieDbDto.FavoriteMovieDto
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDbDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteMovie(favoriteMovieDto: FavoriteMovieDto): Long

    @Query("SELECT * FROM FavoriteMovieDto")
    fun getAllFavoriteMovie(): Flow<List<FavoriteMovieDto>>
}