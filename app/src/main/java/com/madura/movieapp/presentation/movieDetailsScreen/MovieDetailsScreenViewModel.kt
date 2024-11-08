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
import com.madura.movieapp.data.dto.movieDbDto.FavoriteMovieDto
import com.madura.movieapp.data.dto.movieDetailsDto.Movie
import com.madura.movieapp.domain.use_case.get_favorite_movies.GetFavoriteMovieUseCase
import com.madura.movieapp.domain.use_case.get_movieDetails.GetMovieDetailsUseCase
import com.madura.movieapp.domain.use_case.get_movie_suggestions.GetMovieSuggestionsUseCase
import com.madura.movieapp.domain.use_case.insert_to_favorite_movie.InsertToFavoriteMovieUseCase
import com.madura.movieapp.domain.use_case.remove_favorite_movie.RemoveFavoriteMovieUseCase
import com.madura.movieapp.presentation.favorite_movies_screen.FavoriteMovieListState
import com.madura.movieapp.presentation.favorite_movies_screen.RemoveFavoriteMovieState
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
    private val getFavoriteMovieUseCase: GetFavoriteMovieUseCase,
    private val removeFavoriteMovieUseCase: RemoveFavoriteMovieUseCase
) : ViewModel() {
    private val TAG = "MovieDetailsScreenViewModel"

    private val _movieDetailsState = mutableStateOf(MovieDetailsState())
    val movieDetailsState: State<MovieDetailsState> = _movieDetailsState

    private val _movieSuggestionState = mutableStateOf(MovieSuggestionState())
    val movieSuggestionState: State<MovieSuggestionState> = _movieSuggestionState

    private val _removeMovieState = mutableStateOf(RemoveFavoriteMovieState())
    val removeMovieState: State<RemoveFavoriteMovieState> = _removeMovieState

    private val _insertToFavoriteState = mutableStateOf(InsertToFavoriteState())
    val insertToFavoriteState: State<InsertToFavoriteState> = _insertToFavoriteState

    var movie: FavoriteMovieDto? = null

    init {
        savedStateHandle.get<String>(Constants.PARAM_MOVIE_ID)?.let { movieId ->
            Log.d(TAG, "movie id --->> $movieId")
            getFavoriteMovieById(movieId = movieId.toInt())
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
                        var movieDetails = result.data.data.movie
                        movieDetails.isFavorite = movie != null

                        _movieDetailsState.value =
                            MovieDetailsState(movieDetails = movieDetails);

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

    private fun getFavoriteMovieById(movieId: Int) {
        try {
            getFavoriteMovieUseCase().onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        val movies = result.data
                        Log.d(TAG, "getFavoriteMovieById: $movies")

                        if (movies.isNullOrEmpty()) return@onEach

                        movies.find { it.movieId == movieId }.let {
                            movie = it
                            Log.d(TAG, " found getFavoriteMovieById: $it")
                            return@onEach
                        }
                    }

                    is Resource.Error -> {
                        return@onEach
                    }

                    is Resource.Loading -> {

                    }
                }
            }.launchIn(viewModelScope)

        } catch (e: Exception) {
            return
        }
        return
    }

    fun insertToFavoriteMovie(movie: FavoriteMovieDto) {
        viewModelScope.launch {
            insertToFavoriteMovieUseCase(movie = movie).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        try {
                            Log.d(TAG, "movie suggestion --->>${result.data.toString()}")
                            if (result.data != null && result.data > 0 && _movieDetailsState.value.movieDetails != null) {
                                _movieDetailsState.value = _movieDetailsState.value.copy(
                                    movieDetails = _movieDetailsState.value.movieDetails!!.copy(
                                        isFavorite = true
                                    )

                                )
                                _insertToFavoriteState.value =
                                    InsertToFavoriteState(id = result.data)
                                Log.d(
                                    TAG,
                                    "insertToFavoriteMovie: ${_movieDetailsState.value.movieDetails!!.isFavorite}"
                                )
                            }

                        } catch (e: Exception) {
                            e.printStackTrace()
                            _insertToFavoriteState.value = InsertToFavoriteState(
                                error = result.message ?: "An unexpected error occurred"
                            )
                        }

                    }

                    is Resource.Error -> {
                        _insertToFavoriteState.value = InsertToFavoriteState(
                            error = result.message ?: "An unexpected error occurred"
                        )
                    }

                    is Resource.Loading -> {
                        _insertToFavoriteState.value = InsertToFavoriteState(
                            isLoading = true
                        )
                    }
                }
            }.launchIn(viewModelScope)
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
//                            _removeMovieState.value = RemoveFavoriteMovieState(
//                                isLoading = true
//                            )
                        }

                        is Resource.Success -> {
                            try {
                                val removeMovieResult =
                                    result.data
                                Log.d(TAG, "removeFavoriteMovie result: $removeMovieResult")
                                if (removeMovieResult != null && removeMovieResult > 0) {

                                    _movieDetailsState.value = _movieDetailsState.value.copy(
                                        movieDetails = _movieDetailsState.value.movieDetails!!.copy(
                                            isFavorite = false
                                        )

                                    )

                                    _removeMovieState.value = RemoveFavoriteMovieState(
                                        movieId = movieId
                                    )


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
                _removeMovieState.value = RemoveFavoriteMovieState(
                    error = "An unexpected error occurred"
                )
            }
        }
    }

}