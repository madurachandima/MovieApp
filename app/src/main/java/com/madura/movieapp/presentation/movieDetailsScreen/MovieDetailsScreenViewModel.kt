package com.madura.movieapp.presentation.movieDetailsScreen

import MovieSuggestionState
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.madura.movieapp.common.Constants
import com.madura.movieapp.common.Resource
import com.madura.movieapp.data.dto.movieDbDto.FavoriteMovieDto
import com.madura.movieapp.data.dto.movieDetailsDto.Movie
import com.madura.movieapp.domain.use_case.get_local_movie_by_id.GetLocalMovieByIdUseCase
import com.madura.movieapp.domain.use_case.get_movieDetails.GetMovieDetailsUseCase
import com.madura.movieapp.domain.use_case.get_movie_suggestions.GetMovieSuggestionsUseCase
import com.madura.movieapp.domain.use_case.insert_to_favorite_movie.InsertToFavoriteMovieUseCase
import com.madura.movieapp.domain.use_case.remove_movie.RemoveMovieUseCase
import com.madura.movieapp.domain.use_case.update_favorite_movie.UpdateFavoriteMovieStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieSuggestionsUseCase: GetMovieSuggestionsUseCase,
    private val insertToFavoriteMovieUseCase: InsertToFavoriteMovieUseCase,
    private val getLocalMovieByIdUseCase: GetLocalMovieByIdUseCase,
    private val removeMovieUseCase: RemoveMovieUseCase,
    private val updateFavoriteMovieUseCase: UpdateFavoriteMovieStatusUseCase
) : ViewModel() {
    private val TAG = "MovieDetailsScreenViewModel"

    private val _movieDetailsState = mutableStateOf(MovieDetailsState())
    val movieDetailsState: State<MovieDetailsState> = _movieDetailsState

    private val _movieSuggestionState = mutableStateOf(MovieSuggestionState())
    val movieSuggestionState: State<MovieSuggestionState> = _movieSuggestionState

    private var movieDetails: Movie? = null
    private var localMovieDetails: FavoriteMovieDto? = null

    init {
        savedStateHandle.get<String>(Constants.PARAM_MOVIE_ID)?.let { movieId ->
            Log.d(TAG, "movie id --->> $movieId")
            getMovieDetailsById(movieId = movieId.toInt())
            getMovieSuggestionById(movieId = movieId.toInt())
        }
    }

    private fun getMovieDetailsById(movieId: Int) {

        viewModelScope.launch {
            localMovieDetails = getLocalFavoriteMovieById(movieId = movieId)
        }

        getMovieDetailsUseCase(movieId = movieId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    try {
                        Log.d(TAG, "movie details --->>${result.data!!.data.toString()}")
                        val movieDetails = result.data.data.movie
                        if (localMovieDetails != null) {
                            movieDetails.isFavorite = localMovieDetails!!.isFavorite
                        }

                        _movieDetailsState.value = MovieDetailsState(movieDetails = movieDetails)

                        this.movieDetails = movieDetails

                    } catch (e: Exception) {
                        Log.e(TAG, "error ->$e")
                        _movieDetailsState.value = MovieDetailsState(
                            error = result.message ?: "An unexpected error occurred"
                        )
                    }
                }

                is Resource.Loading -> {
                    _movieDetailsState.value = MovieDetailsState(
                        isLoading = true
                    )
                }

                is Resource.Error -> {
                    _movieDetailsState.value = MovieDetailsState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getMovieSuggestionById(movieId: Int) {
        getMovieSuggestionsUseCase(movieId = movieId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    try {
                        Log.d(TAG, "movie suggestion --->>${result.data!!.data.toString()}")
                        _movieSuggestionState.value =
                            MovieSuggestionState(suggestions = result.data.data!!.movies);

                    } catch (e: Exception) {
                        Log.e(TAG, "error ->$e")
                        _movieSuggestionState.value = MovieSuggestionState(
                            error = result.message ?: "An unexpected error occurred"
                        )
                    }
                }

                is Resource.Loading -> {
                    _movieSuggestionState.value = MovieSuggestionState(
                        isLoading = true
                    )
                }

                is Resource.Error -> {
                    _movieSuggestionState.value = MovieSuggestionState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }


    private suspend fun getLocalFavoriteMovieById(movieId: Int): FavoriteMovieDto? {
        var localMovie: FavoriteMovieDto? = null

        kotlin.runCatching {
            getLocalMovieByIdUseCase(movieId = movieId).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        localMovie = result.data
                        Log.d(TAG, "getFavoriteMovieById: $localMovie")
                    }

                    is Resource.Error -> {
                        localMovie = null

                    }

                    is Resource.Loading -> {
                        _movieDetailsState.value = MovieDetailsState(
                            isLoading = true
                        )
                    }
                }
            }

        }.onFailure {
            Log.e(TAG, "getLocalFavoriteMovieById: $it")
        }

        return localMovie
    }

    private fun insertToFavoriteMovieToLocal(movie: FavoriteMovieDto) {
        viewModelScope.launch {
            insertToFavoriteMovieUseCase(movie = movie).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        try {
                            if (result.data != null) {

                                _movieDetailsState.value = MovieDetailsState(
                                    movieDetails = movieDetails!!.copy(isFavorite = true)
                                )
                                localMovieDetails = result.data

                                Log.d(
                                    TAG,
                                    "insertToFavoriteMovie: ${_movieDetailsState.value.movieDetails!!.isFavorite}"
                                )

                            }

                        } catch (e: Exception) {
                            e.printStackTrace()
                            _movieDetailsState.value = MovieDetailsState(
                                error = result.message ?: "An unexpected error occurred"
                            )
                        }

                    }

                    is Resource.Error -> {
                        _movieDetailsState.value = MovieDetailsState(
                            error = result.message ?: "An unexpected error occurred"
                        )
                    }

                    is Resource.Loading -> {
                        _movieDetailsState.value = MovieDetailsState(
                            isLoading = true
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun removeFavoriteMovieFromLocal(movieId: Int) {
        Log.d(TAG, "removeFavoriteMovie: $movieId")
        viewModelScope.launch {
            try {
                removeMovieUseCase(movieId = movieId).collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            Log.d(TAG, "removeFavoriteMovie: Loading")
                            _movieDetailsState.value = MovieDetailsState(
                                isLoading = true
                            )
                        }

                        is Resource.Success -> {
                            try {
                                val removeMovieResult = result.data
                                Log.d(TAG, "removeFavoriteMovie result: $removeMovieResult")
                                if (removeMovieResult != null && removeMovieResult > 0) {

                                    _movieDetailsState.value = MovieDetailsState(
                                        movieDetails = movieDetails!!.copy(isFavorite = false)
                                    )
                                    localMovieDetails = null
                                }

                            } catch (e: Exception) {
                                e.printStackTrace()
                                _movieDetailsState.value = MovieDetailsState(
                                    error = "An unexpected error occurred"
                                )
                            }
                        }

                        is Resource.Error -> {
                            Log.d(TAG, "removeFavoriteMovie: Error")
                            _movieDetailsState.value = MovieDetailsState(
                                error = "An unexpected error occurred"
                            )
                        }


                    }

                }
            } catch (e: Exception) {
                e.printStackTrace()
                _movieDetailsState.value = MovieDetailsState(
                    error = "An unexpected error occurred"
                )
            }
        }
    }

    fun updateFavoriteMovieStatus(
        movie: FavoriteMovieDto, isFavorite: Boolean, isWatched: Boolean = false
    ) {
        Log.d(
            TAG,
            "localMovieDetails is null =${localMovieDetails == null}  id -> ${movie.id}  updateFavoriteMovie id: ${movie.movieId} isFavorite - > $isFavorite isWatched -> $isWatched"
        )
        viewModelScope.launch {
            if (localMovieDetails == null) {
                Log.d(TAG, "<<<<<<<---------- Movie inserted --------->>>>>>>.")
                insertToFavoriteMovieToLocal(movie)
                return@launch
            }

            if (!isFavorite && !isWatched) {
                Log.d(TAG, "<<<<<<<---------- Movie removed --------->>>>>>>.")
                removeFavoriteMovieFromLocal(movieId = movie.movieId)
                return@launch

            }
            Log.d(TAG, "<<<<<<<---------- Movie updated --------->>>>>>>.")
            updateLocalFavoriteMovie(movie = movie, isFavorite = isFavorite)
        }
    }

    private fun updateLocalFavoriteMovie(
        movie: FavoriteMovieDto,
        isFavorite: Boolean,
        isWatched: Boolean = false
    ) {
        viewModelScope.launch {
            try {
                updateFavoriteMovieUseCase(
                    movieId = movie.movieId, isFavorite = isFavorite
                ).collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            Log.d(TAG, "updateFavoriteMovie: Loading")
                            _movieDetailsState.value = MovieDetailsState(
                                isLoading = true
                            )
                        }

                        is Resource.Success -> {
                            try {
                                val updateMovieStatusResult = result.data
                                Log.d(
                                    TAG, "updateFavoriteMovie result: $updateMovieStatusResult"
                                )
                                if (updateMovieStatusResult != null && movieDetails != null) {
                                    _movieDetailsState.value = MovieDetailsState(
                                        movieDetails = movieDetails!!.copy(isFavorite = updateMovieStatusResult.isFavorite)
                                    )

                                }

                            } catch (e: Exception) {
                                e.printStackTrace()
                                _movieDetailsState.value = MovieDetailsState(
                                    error = "An unexpected error occurred"
                                )
                            }
                        }

                        is Resource.Error -> {
                            Log.d(TAG, "updateFavoriteMovie: Error")
                            _movieDetailsState.value = MovieDetailsState(
                                error = "An unexpected error occurred"
                            )
                        }


                    }

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

}