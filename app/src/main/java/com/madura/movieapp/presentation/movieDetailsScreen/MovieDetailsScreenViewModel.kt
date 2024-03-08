package com.madura.movieapp.presentation.movieDetailsScreen

import MovieSuggestionState
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madura.movieapp.common.Constants
import com.madura.movieapp.common.Resource
import com.madura.movieapp.domain.use_case.get_movieDetails.GetMovieDetailsUseCase
import com.madura.movieapp.domain.use_case.get_movie_suggestions.GetMovieSuggestionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieDetailsScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieSuggestionsUseCase: GetMovieSuggestionsUseCase,

    ) : ViewModel() {
    private val TAG = "MovieDetailsScreenViewModel"

    private val _movieDetailsState = mutableStateOf(MovieDetailsState())
    val movieDetailsState: State<MovieDetailsState> = _movieDetailsState

    private val _movieSuggestionState = mutableStateOf(MovieSuggestionState())
    val movieSuggestionState: State<MovieSuggestionState> = _movieSuggestionState


    init {
        savedStateHandle.get<String>(Constants.PARAM_MOVIE_ID)?.let { movieId ->
            Log.d(TAG, "movie id --->> $movieId")
            getMovieDetailsById(movieId = movieId.toInt())
            getMovieSuggestionById(movieId = movieId.toInt())
        }
    }

    private fun getMovieDetailsById(movieId: Int) {
        getMovieDetailsUseCase(movieId = movieId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    try {
                        Log.d(TAG, "movie details --->>${result.data!!.data.toString()}")
                        _movieDetailsState.value =
                            MovieDetailsState(movieDetails = result.data.data.movie);

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


}