package com.madura.movieapp.presentation.favorite_movies_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madura.movieapp.common.Resource
import com.madura.movieapp.domain.use_case.get_favorite_movies.GetFavoriteMovieUseCase
import com.madura.movieapp.domain.use_case.remove_favorite_movie.RemoveFavoriteMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteMovieScreenViewModel @Inject constructor(
    private val getFavoriteMovieUseCase: GetFavoriteMovieUseCase,
    private val removeFavoriteMovieUseCase: RemoveFavoriteMovieUseCase
) : ViewModel() {

    private val _state = mutableStateOf(FavoriteMovieListState())
    val state: State<FavoriteMovieListState> = _state

    private val _removeMovieState = mutableStateOf(RemoveFavoriteMovieState())
    val removeMovieState: State<RemoveFavoriteMovieState> = _removeMovieState

    val TAG = "FavoriteMovieScreenViewModel"

     fun getFavoriteMovies() {

        try {
            getFavoriteMovieUseCase().onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        try {
                            val movies = result.data
                            _state.value = FavoriteMovieListState(movies = movies)
                        } catch (e: Exception) {
                            _state.value = FavoriteMovieListState(
                                error = result.message ?: "An unexpected error occurred"
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.value = FavoriteMovieListState(
                            error = result.message ?: "An unexpected error occurred"
                        )
                    }

                    is Resource.Loading -> {
                        _state.value = FavoriteMovieListState(
                            isLoading = true
                        )
                    }
                }
            }.launchIn(viewModelScope)

        } catch (e: Exception) {
            e.printStackTrace()
            _state.value = FavoriteMovieListState(
                error = "An unexpected error occurred"
            )
        }
    }

    fun removeFavoriteMovie(movieId: Int) {
        Log.d(TAG, "removeFavoriteMovie: $movieId")
        viewModelScope.launch {
            try {
                removeFavoriteMovieUseCase(movieId = movieId).collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            Log.d(TAG, "removeFavoriteMovie: Loading")
                            _removeMovieState.value = RemoveFavoriteMovieState(
                                isLoading = true
                            )
                        }

                        is Resource.Success -> {
                            try {
                                val removeMovieResult =
                                    result.data
                                Log.d(TAG, "removeFavoriteMovie result: $removeMovieResult")
                                if (removeMovieResult !=null && removeMovieResult > 0) {
                                    _removeMovieState.value = RemoveFavoriteMovieState(
                                        movieId = removeMovieResult
                                    )
                                    val updatedMovieList = _state.value.movies?.filter {
                                        it.movieId != movieId
                                    }
                                    _state.value = _state.value.copy(movies = updatedMovieList)

                                }

                            } catch (e: Exception) {
                                e.printStackTrace()
                                _removeMovieState.value = RemoveFavoriteMovieState(
                                    error = "An unexpected error occurred"
                                )
                            }
                        }

                        is Resource.Error -> {
                            Log.d(TAG, "removeFavoriteMovie: Error")
                            _removeMovieState.value = RemoveFavoriteMovieState(
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