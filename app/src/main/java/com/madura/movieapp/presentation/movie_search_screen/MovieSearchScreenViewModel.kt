package com.madura.movieapp.presentation.movie_search_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madura.movieapp.common.Resource
import com.madura.movieapp.data.dto.movieListDto.Movie
import com.madura.movieapp.domain.use_case.get_movies.GetMoveUseCase
import com.madura.movieapp.presentation.homeScreen.MovieListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieSearchScreenViewModel @Inject constructor(
    private val getMoveUseCase: GetMoveUseCase,
) : ViewModel() {
    private val TAG = "MovieSearchScreenViewModel"

    private val _state = mutableStateOf(MovieListState())
    val state: State<MovieListState> = _state

    private var page: Int = 1

    var movieList = mutableStateOf<ArrayList<Movie>>(arrayListOf())

    var query: String = ""

    init {
        movieList.value.clear()
    }

    fun getMovies(
        sortBy: String? = null,
        genre: String? = null,
        resetPage: Boolean = false,
        query: String,
    ) {

        this.query = query
        if (resetPage) {
            movieList.value.clear()
            page = 1
        }

        getMoveUseCase(
            query = query,
            page = page,
            sortBy = sortBy,
            genre = genre
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    try {
                        val movie = result.data
                        if (resetPage) movieList.value =
                            movie!!.data!!.movies else movieList.value.addAll(movie!!.data!!.movies)
                        Log.d(TAG, "movie list ---------> ${movieList.value.size}")
                        _state.value = MovieListState(movies = movieList.value)
                        page++

                    } catch (e: Exception) {
                        _state.value =
                            MovieListState(error = result.message ?: "An unexpected error occurred")

                    }

                }

                is Resource.Error -> {
                    _state.value =
                        MovieListState(error = result.message ?: "An unexpected error occurred")

                }

                is Resource.Loading -> {
                    _state.value = MovieListState(
                        isLoading = if (resetPage) true else movieList.value.isEmpty(),
                        isPaginationLoading = movieList.value.isNotEmpty()
                    )

                }

            }
        }.launchIn(viewModelScope)


    }
}