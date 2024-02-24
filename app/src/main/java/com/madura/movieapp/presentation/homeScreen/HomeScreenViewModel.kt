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

    private var movieListDto: MovieListDto? = MovieListDto()
    private var page: Int = 1
    private val query: String = ""

    var movieList = mutableStateOf<ArrayList<Movie>>(arrayListOf())


    //ArrayList<Movie> = arrayListOf()


    init {
        getMovies()
    }


    fun getMovies() {
//        if (itemIndex == null) {
//            getMoveUseCase(query = query, page = page).onEach { result ->
//                when (result) {
//                    is Resource.Success -> {
//                        movieListDto = result.data
//                        movieList.value = movieListDto!!.data!!.movies
//                        _state.value = MovieListState(movies = movieList.value)
//                        page++
//                    }
//
//                    is Resource.Error -> {
//                        _state.value =
//                            MovieListState(error = result.message ?: "An unexpected error occurred")
//                    }
//
//                    is Resource.Loading -> {
//                        _state.value = MovieListState(isLoading = true)
//                    }
//
//                }
//            }.launchIn(viewModelScope)
//        }


        getMoveUseCase(query = query, page = page).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val movie = result.data
                    movieList.value.addAll(movie!!.data!!.movies)
                    Log.d(TAG, "length ----->>>> ${movieList.value.size}")
                    _state.value = MovieListState(movies = movieList.value)
                    page++
                }

                is Resource.Error -> {
                    _state.value =
                        MovieListState(error = result.message ?: "An unexpected error occurred")
                }

                is Resource.Loading -> {
                    _state.value = MovieListState(
                        isLoading = movieList.value.isEmpty(),
                        isPaginationLoading = movieList.value.isNotEmpty()
                    )
                }

            }
        }.launchIn(viewModelScope)


    }
}