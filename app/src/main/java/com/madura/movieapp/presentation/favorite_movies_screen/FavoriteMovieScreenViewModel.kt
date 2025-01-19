package com.madura.movieapp.presentation.favorite_movies_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madura.movieapp.common.Resource
import com.madura.movieapp.data.dto.movieDbDto.FavoriteMovieDto
import com.madura.movieapp.domain.use_case.get_favorite_movies.GetFavoriteMovieUseCase
import com.madura.movieapp.domain.use_case.remove_movie.RemoveMovieUseCase
import com.madura.movieapp.domain.use_case.update_favorite_movie.UpdateFavoriteMovieStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteMovieScreenViewModel @Inject constructor(
    private val getFavoriteMovieUseCase: GetFavoriteMovieUseCase,
    private val removeMovieUseCase: RemoveMovieUseCase,
    private val updateFavoriteMovieUseCase: UpdateFavoriteMovieStatusUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(FavoriteMovieListState())
    val state: State<FavoriteMovieListState> = _state


    private val _updateFavoriteMovieState = mutableStateOf(MovieState())
    val updateFavoriteMovieState: State<MovieState> = _updateFavoriteMovieState

    val TAG = "FavoriteMovieScreenViewModel"

    fun getFavoriteMovies() {
        Log.d(TAG, "call ---->>>> getFavoriteMovies")
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

    private fun removeFavoriteMovie(movieId: Int) {
        Log.d(TAG, "removeFavoriteMovie: $movieId")
        viewModelScope.launch {
            try {
                removeMovieUseCase(movieId = movieId).collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            Log.d(TAG, "removeFavoriteMovie: Loading")

                        }

                        is Resource.Success -> {
                            try {
                                val removeMovieResult =
                                    result.data
                                Log.d(TAG, "removeFavoriteMovie result: $removeMovieResult")
                                if (removeMovieResult != null && removeMovieResult > 0) {

                                    val updatedMovieList = _state.value.movies?.filter {
                                        it.movieId != movieId
                                    }
                                    _state.value = _state.value.copy(movies = updatedMovieList)

                                }

                            } catch (e: Exception) {
                                e.printStackTrace()
                                _updateFavoriteMovieState.value = MovieState(
                                    error = "An unexpected error occurred"
                                )
                            }
                        }

                        is Resource.Error -> {
                            Log.d(TAG, "removeFavoriteMovie: Error")
                            _updateFavoriteMovieState.value = MovieState(
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

    fun updateFavoriteMovieStatus(movieId: Int, isFavorite: Boolean) {
        Log.d(TAG, "updateFavoriteMovie: $movieId state - > $isFavorite")

        viewModelScope.launch {
            val movie: FavoriteMovieDto? = _state.value.movies?.find { it.movieId == movieId }

            if (movie != null && !isFavorite && !movie.isWatched) {
                removeFavoriteMovie(movieId)
                return@launch
            }

            try {
                updateFavoriteMovieUseCase(
                    movieId = movieId,
                    isFavorite = isFavorite
                ).collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            Log.d(TAG, "updateFavoriteMovie: Loading")

                        }

                        is Resource.Success -> {
                            try {
                                val updateMovieStatusResult =
                                    result.data
                                Log.d(TAG, "updateFavoriteMovie result: $updateMovieStatusResult")
                                if (updateMovieStatusResult != null) {
                                    _updateFavoriteMovieState.value = MovieState(
                                        movie = updateMovieStatusResult
                                    )
                                    val updatedMovieList = _state.value.movies?.filter {
                                        it.movieId != movieId
                                    }
                                    _state.value = _state.value.copy(movies = updatedMovieList)

                                    Log.d(
                                        TAG,
                                        "updateFavoriteMovie result movie id : ${updateMovieStatusResult.movieId} --> is favorite ${updateMovieStatusResult.isFavorite}"
                                    )

                                }

                            } catch (e: Exception) {
                                e.printStackTrace()
                                _updateFavoriteMovieState.value = MovieState(
                                    error = "An unexpected error occurred"
                                )
                            }
                        }

                        is Resource.Error -> {
                            Log.d(TAG, "updateFavoriteMovie: Error")
                            _updateFavoriteMovieState.value = MovieState(
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