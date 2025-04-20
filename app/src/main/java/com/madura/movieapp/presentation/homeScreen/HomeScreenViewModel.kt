package com.madura.movieapp.presentation.homeScreen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madura.movieapp.common.MediaType
import com.madura.movieapp.common.Resource
import com.madura.movieapp.data.dto.tmdb.genre.Genre
import com.madura.movieapp.data.dto.tmdb.mediaByGenre.Result
import com.madura.movieapp.domain.use_case.get_media_by_genre_id.GetMediaByGenreIdUseCase
import com.madura.movieapp.domain.use_case.get_movie_genre.GetMovieGenreUseCase
import com.madura.movieapp.domain.use_case.get_movies.GetMoveUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getMoveUseCase: GetMoveUseCase,
    private val getMoveGenreUseCase: GetMovieGenreUseCase,
    private val getMediaByGenreIdUseCase: GetMediaByGenreIdUseCase
) : ViewModel() {

    private val TAG = "HomeScreenViewModel"

    private val _genreState = mutableStateOf(HomeMovieGenreState())
    val genreState: State<HomeMovieGenreState> = _genreState

    private val _popularMovieState = mutableStateOf(PopularMovieListState())
    val popularMovieState: State<PopularMovieListState> = _popularMovieState

    private val _mediaByGenreState = mutableStateOf(MediaByGenreState())
    val mediaByGenreState: State<MediaByGenreState> = _mediaByGenreState


    var movieGenres = mutableStateOf<List<Genre>>(listOf())
    var tvSeriesGenres = mutableStateOf<List<Genre>>(listOf())

    var movieOrTvSeries = mutableStateOf<ArrayList<Result?>?>(arrayListOf())

    private var page: Int = 1
    private var mediaType: String = ""
    private var genreId: String = ""


    init {
        page = 1
        callInit()
        Log.d(TAG, "HomeScreenViewModel init")
    }

    private fun callInit() {
        viewModelScope.launch {
            val movieGenresDeferred = async { getGenres(mediaType = MediaType.Movies.name) }
            val tvGenresDeferred = async { getGenres(mediaType = MediaType.tv.name) }
            movieGenresDeferred.await()
            tvGenresDeferred.await()

            if (movieGenres.value.isNotEmpty()) {
                Log.d(TAG, "HomeScreenViewModel init 2")
                getMediaByGenreId(
                    mediaType = MediaType.Movies.name,
                    genreId = movieGenres.value.first().id.toString(),
                )
            }
            getTrendingMovies()
        }
    }

    private fun getTrendingMovies() {
        getMoveUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    try {
                        _popularMovieState.value =
                            PopularMovieListState(sortedMovies = result.data!!.trendingResults)

                    } catch (e: Exception) {
                        _popularMovieState.value =
                            PopularMovieListState(
                                error = result.message ?: "An unexpected error occurred"
                            )
                    }

                }

                is Resource.Error -> {
                    _popularMovieState.value =
                        PopularMovieListState(
                            error = result.message ?: "An unexpected error occurred"
                        )
                }

                is Resource.Loading -> {
                    _popularMovieState.value =
                        PopularMovieListState(isLoading = true)
                }

            }
        }.launchIn(viewModelScope)

    }

    private fun getGenres(
        mediaType: String,
    ) {
        getMoveGenreUseCase(genre = mediaType).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    try {
                        _genreState.value = HomeMovieGenreState(genres = result.data)
                        if (mediaType == MediaType.Movies.name) {
                            movieGenres.value = result.data!!.genres
                            if (movieGenres.value.isNotEmpty()) {
                                Log.d(TAG, "HomeScreenViewModel init 2")
                                getMediaByGenreId(
                                    mediaType = MediaType.Movies.name,
                                    genreId = movieGenres.value.first().id.toString(),
                                )
                            }
                        } else {
                            tvSeriesGenres.value = result.data!!.genres
                        }
                    } catch (e: Exception) {
                        _genreState.value =
                            HomeMovieGenreState(
                                error = result.message ?: "An unexpected error occurred"
                            )
                    }

                }

                is Resource.Error -> {
                    _genreState.value =
                        HomeMovieGenreState(
                            error = result.message ?: "An unexpected error occurred"
                        )
                }

                is Resource.Loading -> {
                    _genreState.value =
                        HomeMovieGenreState(isLoading = true)
                }

            }
        }.launchIn(viewModelScope)

    }

    fun getMediaByGenreId(
        mediaType: String,
        genreId: String,

        ) {
        if (this.mediaType != mediaType) {
            this.mediaType = mediaType
            page = 1
        }

        if (this.genreId != genreId) {
            this.genreId = genreId
            page = 1
        }

        getMediaByGenreIdUseCase(
            genreId = this.genreId,
            this.mediaType,
            page = this.page
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    try {
                        if (result.data!!.results != null) {
                            _mediaByGenreState.value =
                                MediaByGenreState(movies = result.data.results!!)

                            if (page == 1) {
                                movieOrTvSeries.value = result.data.results
                            } else {
                                movieOrTvSeries.value?.addAll(result.data.results)
                            }
                            page += 1
                        }
                    } catch (e: Exception) {
                        _mediaByGenreState.value =
                            MediaByGenreState(
                                error = result.message ?: "An unexpected error occurred"
                            )
                    }

                }

                is Resource.Error -> {
                    _mediaByGenreState.value =
                        MediaByGenreState(
                            error = result.message ?: "An unexpected error occurred"
                        )
                }

                is Resource.Loading -> {
                    if (page == 1) {
                        _mediaByGenreState.value =
                            MediaByGenreState(isLoading = true)
                    } else {
                        _mediaByGenreState.value =
                            MediaByGenreState(isPaginationLoading = true)
                    }

                }

            }
        }.launchIn(viewModelScope)
    }
}