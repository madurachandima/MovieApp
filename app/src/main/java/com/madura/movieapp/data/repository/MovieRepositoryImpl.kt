package com.madura.movieapp.data.repository

import android.util.Log
import com.madura.movieapp.data.dto.movieDetailsDto.MovieDetailsDto
import com.madura.movieapp.data.dto.movieListDto.MovieListDto
import com.madura.movieapp.data.dto.tmdb.genre.Genres
import com.madura.movieapp.data.dto.tmdb.mediaByGenre.MediaByGenre
import com.madura.movieapp.data.dto.tmdb.trending.Trending
import com.madura.movieapp.data.remote.MovieAppApi
import com.madura.movieapp.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieAppApi,
) : MovieRepository {
    override suspend fun getMovies(
        query: String?,
        page: Int?,
        sortBy: String?,
        genre: String?,

        ): MovieListDto {
        return api.getMovieList(
            limit = 20,
            query = query ?: "",
            page = page ?: 1,
            sortBy = sortBy ?: "date_added",
            genre = genre
        )
    }

    override suspend fun getMovieDetailsById(
        movieId: Int,
        withImage: Boolean?,
        withCast: Boolean?,
    ): MovieDetailsDto {
        return api.getMovieDetailById(
            movieId = movieId,
            withImage = withImage ?: true,
            withCast = withCast ?: true
        )
    }

    override suspend fun getMovieSuggestionsById(movieId: Int): MovieListDto {
        return api.getMovieSuggestionsById(
            movieId = movieId,
        )
    }

    override suspend fun getPopularMoviesAndTvShowsByWeek(): Trending {
        return api.getPopularMoviesAndTvShowsByWeek()
    }

    override suspend fun getGenre(genre: String): Genres {
        val genreType = if (genre == "movie" || genre == "Movies") {
            "movie"
        } else {
            "tv"
        }
        return api.getGenre(genre = genreType)
    }

    override suspend fun getMovieOrTvSeriesByGenre(
        mediaType: String,
        genreId: String,
        page: Int
    ): MediaByGenre {
        val type = if (mediaType == "movie" || mediaType == "Movies") {
            "movie"
        } else {
            "tv"
        }

        return api.getMovieOrTvSeriesByGenre(genreId = genreId, mediaType = type, page = page)
    }
}