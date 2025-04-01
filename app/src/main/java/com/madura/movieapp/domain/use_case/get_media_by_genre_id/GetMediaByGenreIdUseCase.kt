package com.madura.movieapp.domain.use_case.get_media_by_genre_id

import android.util.Log
import com.madura.movieapp.common.Resource
import com.madura.movieapp.data.dto.tmdb.mediaByGenre.MediaByGenre
import com.madura.movieapp.data.repository.MovieRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMediaByGenreIdUseCase @Inject constructor(
    private val movieRepositoryImpl: MovieRepositoryImpl
) {
    val TAG = "GetMediaByGenreId"

    operator fun invoke(genreId: String, mediaType: String): Flow<Resource<MediaByGenre>> = flow {
        try {
            emit(Resource.Loading<MediaByGenre>())
            val result = movieRepositoryImpl.getMovieOrTvSeriesByGenre(
                genreId = genreId,
                mediaType = mediaType
            )
            Log.d(TAG, "GetMediaByGenreId invoke result: $result")
            emit(Resource.Success<MediaByGenre>(data = result))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(
                Resource.Error<MediaByGenre>(
                    message = e.message ?: "An unexpected error occurred"
                )
            )

        }
    }

}