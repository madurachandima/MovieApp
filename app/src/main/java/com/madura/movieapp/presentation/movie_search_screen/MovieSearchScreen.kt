package com.madura.movieapp.presentation.movie_search_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.madura.movieapp.presentation.Screen
import com.madura.movieapp.presentation.composible.MovieItem
import com.madura.movieapp.presentation.composible.searchTextField
import com.madura.movieapp.presentation.homeScreen.HomeScreenViewModel
import com.madura.movieapp.ui.composable.OnBottomReached
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MovieSearchScreen(
    navController: NavController,
    viewModel: MovieSearchScreenViewModel = hiltViewModel(),
) {

    val state = viewModel.state.value

    val gridState = rememberLazyGridState()

    var searchJob: Job? = null


    val coroutineScope = rememberCoroutineScope()

    var searchQuery by remember {
        mutableStateOf(viewModel.query)
    }



    Scaffold { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 8.dp, end = 8.dp, top = 8.dp)
            ) {
                searchTextField { value ->
                    searchQuery = value

                    if (searchQuery == "" || searchQuery.isEmpty()) {
                        viewModel.movieList.value.clear()
                        viewModel.query = ""
                        return@searchTextField
                    } else {
                        searchJob?.cancel()
                        searchJob = coroutineScope.launch {
                            delay(500)
                            viewModel.getMovies(query = value, resetPage = true)
                        }
                    }

                }

                if (viewModel.movieList.value.isNotEmpty() && !state.isLoading)
                    LazyVerticalGrid(
                        state = gridState,
                        columns = GridCells.Adaptive(minSize = 100.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        itemsIndexed(viewModel.movieList.value) { index, item ->
                            MovieItem(url = item.medium_cover_image, Modifier.clickable {
                                navController.navigate(Screen.MovieDetailsScreen.route + "/${item.id}")
                            })
                        }
                    }
            }

            if (viewModel.movieList.value.isNotEmpty()) {
                gridState.OnBottomReached {
                    coroutineScope.launch {
                        if (searchQuery == "" || searchQuery.isEmpty())
                            viewModel.getMovies(query = searchQuery)
                    }

                }
            }

            if (state.error.isNotBlank()) {
                Text(
                    text = state.error,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 20.dp
                        )
                        .align(Alignment.Center)
                )
            }

            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

    }
}