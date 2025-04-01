package com.madura.movieapp.presentation.movie_search_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.madura.movieapp.data.dto.movieListDto.Movie
import com.madura.movieapp.domain.use_case.get_movies.GetMoveUseCase
import com.madura.movieapp.presentation.homeScreen.MovieListState

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieSearchScreenViewModel @Inject constructor(
    private val getMoveUseCase: GetMoveUseCase,
) : ViewModel() {
    private val TAG = "MovieSearchScreenViewModel"

    private val _state = mutableStateOf(MovieListState())
    val state: State<MovieListState> = _state

    private var page: Int = 1

    var movieList by mutableStateOf<ArrayList<Movie>>(arrayListOf())

    var query: String = ""

    init {
        movieList.clear()
    }

    fun clearSearchValues() {
        if (movieList != null)

            query = ""
        movieList = arrayListOf()
        Log.d(TAG, "data cleared ------>>>>>>")
    }

    fun getMovies(
        sortBy: String? = null,
        genre: String? = null,
        resetPage: Boolean = false,
        query: String,
    ) {

        this.query = query
        if (resetPage) {
            if (movieList != null && movieList.isNotEmpty()) {
                movieList = arrayListOf()
                page = 1
            }

        }

//        getMoveUseCase(
//            query = query,
//            page = page,
//            sortBy = sortBy,
//            genre = genre
//        ).onEach { result ->
//            when (result) {
//                is Resource.Success -> {
//                    try {
//                        val movie = result.data
//                        if (movie?.data?.movies != null) {
//                            if (resetPage) {
//                                movieList =
//                                    movie.data.movies
//                            } else {
//                                movieList.addAll(
//                                    movie.data.movies
//                                )
//                            }
//                        } else {
//                            movieList = arrayListOf()
//                        }
//
//
//                        _state.value =
//                            MovieListState(movies = if (movieList != null) movieList else arrayListOf())
//                        if (movieList != null && movieList.isNotEmpty()) page++
//
//                    } catch (e: Exception) {
//                        Log.d(TAG, "getMovies: ${e.message}")
//                        _state.value =
//                            MovieListState(error = result.message ?: "An unexpected error occurred")
//
//                    }
//
//                }
//
//                is Resource.Error -> {
//                    _state.value =
//                        MovieListState(error = result.message ?: "An unexpected error occurred")
//
//                }
//
//                is Resource.Loading -> {
//                    _state.value = MovieListState(
//                        isLoading = if (resetPage) true else movieList.isEmpty(),
//                        isPaginationLoading = if (movieList == null) false else movieList.isNotEmpty()
//                    )
//
//                }
//
//            }
//        }.launchIn(viewModelScope)


    }
}