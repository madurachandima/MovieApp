package com.madura.movieapp.data.database


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.madura.movieapp.data.converter.RoomConverter
import com.madura.movieapp.data.dao.MovieDbDao
import com.madura.movieapp.data.dto.movieDbDto.FavoriteMovieDto


@Database(entities = [FavoriteMovieDto::class], version = 1)
@TypeConverters(RoomConverter::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDbDao(): MovieDbDao

}