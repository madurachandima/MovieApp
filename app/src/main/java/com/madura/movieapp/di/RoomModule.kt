package com.madura.movieapp.di

import android.content.Context
import androidx.room.Room
import com.madura.movieapp.data.dao.MovieDbDao
import com.madura.movieapp.data.database.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {
    @Provides
    fun provideWordDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder (context, MovieDatabase::class.java, "movieDb").build()
    }

    @Provides
    fun provideWordDao(movieDatabase: MovieDatabase): MovieDbDao {
        return movieDatabase.movieDbDao()
    }
}