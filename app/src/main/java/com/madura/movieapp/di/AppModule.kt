package com.madura.movieapp.di


import com.madura.movieapp.common.Constants
import com.madura.movieapp.data.remote.MovieAppApi
import com.madura.movieapp.data.repository.MovieRepositoryImpl
import com.madura.movieapp.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMovieApi(): MovieAppApi {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(
            GsonConverterFactory.create()
        ).build().create(MovieAppApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(api: MovieAppApi): MovieRepository {
        return MovieRepositoryImpl(api)
    }
}