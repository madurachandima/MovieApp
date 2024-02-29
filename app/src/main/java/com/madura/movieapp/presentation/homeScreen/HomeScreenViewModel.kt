package com.madura.movieapp.presentation.homeScreen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madura.movieapp.common.Resource
import com.madura.movieapp.data.dto.Data
import com.madura.movieapp.data.dto.Movie
import com.madura.movieapp.data.dto.MovieListDto
import com.madura.movieapp.domain.use_case.get_movies.GetMoveUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getMoveUseCase: GetMoveUseCase,
) : ViewModel() {

    private val TAG = "HomeScreenViewModel"
    private val _state = mutableStateOf(MovieListState())
    val state: State<MovieListState> = _state

    private val _popularMovieState = mutableStateOf(PopularMovieListState())
    val popularMovieState: State<PopularMovieListState> = _popularMovieState

    //    private var movieListDto: MovieListDto? = MovieListDto()
    private var page: Int = 1
    private val query: String = ""

    var movieList = mutableStateOf<ArrayList<Movie>>(arrayListOf())

    init {
        getMovies(sortBy = "download_count", enableSort = true)
        getMovies()
    }


    fun getMovies(
        sortBy: String? = null,
        enableSort: Boolean = false,
        genre: String? = null,
        resetPage: Boolean = false,
    ) {
        if (resetPage) page = 1

        getMoveUseCase(
            query = query,
            page = page,
            sortBy = sortBy,
            genre = genre
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    try {
                        if (enableSort) {
                            _popularMovieState.value =
                                PopularMovieListState(sortedMovies = result.data!!.data!!.movies)
                        } else {
                            val movie = result.data
                            if (resetPage) movieList.value.clear()
                            movieList.value.addAll(movie!!.data!!.movies)
                            Log.d(TAG, "movie list ---------> ${movieList.value.size}")
                            _state.value = MovieListState(movies = movieList.value)
                            page++
                        }
                    } catch (e: Exception) {
                        _state.value =
                            MovieListState(error = result.message ?: "An unexpected error occurred")
                        PopularMovieListState(
                            error = result.message ?: "An unexpected error occurred"
                        )
                    }

                }

                is Resource.Error -> {
                    _state.value =
                        MovieListState(error = result.message ?: "An unexpected error occurred")
                    PopularMovieListState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }

                is Resource.Loading -> {
                    _state.value = MovieListState(
                        isLoading = if (resetPage) true else movieList.value.isEmpty(),
                        isPaginationLoading = movieList.value.isNotEmpty()
                    )
                    PopularMovieListState(
                        isLoading = true
                    )
                }

            }
        }.launchIn(viewModelScope)


    }
}