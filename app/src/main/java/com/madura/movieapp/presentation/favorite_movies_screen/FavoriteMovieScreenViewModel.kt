package com.madura.movieapp.presentation.favorite_movies_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madura.movieapp.common.Resource
import com.madura.movieapp.domain.use_case.get_favorite_movies.GetFavoriteMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FavoriteMovieScreenViewModel @Inject constructor(
    private val getFavoriteMovieUseCase: GetFavoriteMovieUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(FavoriteMovieListState())
    val state: State<FavoriteMovieListState> = _state

    val TAG = "FavoriteMovieScreenViewModel"

    init {
        Log.d(TAG, "call FavoriteMovieScreenViewModel init")
        getFavoriteMovies()
    }

    private fun getFavoriteMovies() {

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
}