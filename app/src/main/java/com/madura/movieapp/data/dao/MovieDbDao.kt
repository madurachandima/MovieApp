package com.madura.movieapp.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


//@Dao
//interface MovieDbDao {
//    @Upsert
//    suspend fun upsertMovie(movie: MovieDbDto)
//
//    @Query("SELECT * FROM MovieDb ORDER BY movieId ASC")
//    fun getLocalMovies(): Flow<List<MovieDbDto>>
//}