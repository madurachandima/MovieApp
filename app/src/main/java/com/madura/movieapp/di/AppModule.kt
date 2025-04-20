package com.madura.movieapp.di


import android.content.Context
import androidx.room.Room
import com.madura.movieapp.common.Constants
import com.madura.movieapp.data.remote.MovieAppApi
import com.madura.movieapp.data.repository.MovieRepositoryImpl
import com.madura.movieapp.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMovieApi(): MovieAppApi {
        val interceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer ${Constants.API_KEY}")
                .build()
            chain.proceed(request)
        }

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(
                GsonConverterFactory.create()
            ).build().create(MovieAppApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(api: MovieAppApi): MovieRepository {
        return MovieRepositoryImpl(api)
    }

}