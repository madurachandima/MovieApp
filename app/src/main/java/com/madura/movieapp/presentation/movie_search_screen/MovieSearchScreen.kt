package com.madura.movieapp.presentation.movie_search_screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.madura.movieapp.R
import com.madura.movieapp.presentation.Screen
import com.madura.movieapp.presentation.composible.MovieItem
import com.madura.movieapp.presentation.composible.SearchTextField
import com.madura.movieapp.presentation.theme.white
import com.madura.movieapp.ui.composable.OnBottomReached
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MovieSearchScreen(
    navController: NavController,
    viewModel: MovieSearchScreenViewModel = hiltViewModel(),
) {
    val TAG = "MovieSearchScreen"
    val state = viewModel.state.value

    val gridState = rememberLazyGridState()

    var searchJob: Job? = null


    val coroutineScope = rememberCoroutineScope()

    var searchQuery by remember {
        mutableStateOf(viewModel.query)
    }


    DisposableEffect(key1 = viewModel) {
        onDispose {
            viewModel.clearSearchValues()
        }

    }

    Scaffold { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 8.dp, end = 8.dp, top = 8.dp)
            ) {

                Text(
                    modifier = Modifier.padding(top = 10.dp, start = 10.dp, bottom = 10.dp),
                    text = "Search your movie here",
                    textAlign = TextAlign.Left,
                    style = MaterialTheme.typography.headlineLarge,
                    fontSize = 20.sp,
                    color = white, fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.height(10.dp))

                SearchTextField { value ->
                    searchQuery = value
                    if (searchQuery == "" || searchQuery.isEmpty()) {
                        viewModel.clearSearchValues()
                        return@SearchTextField
                    } else {
                        searchJob?.cancel()
                        searchJob = coroutineScope.launch {
                            delay(500)
                            viewModel.getMovies(query = value, resetPage = true)
                        }
                    }

                }

                if (viewModel.movieList.isEmpty() && !state.isLoading && (searchQuery == "" || searchQuery.isEmpty())) {

                    Box(modifier = Modifier.fillMaxSize()) {
                        Column(modifier = Modifier.align(Alignment.Center)) {
                            Image(
                                painter = painterResource(id = R.drawable.search_here),
                                contentDescription = null,
                                modifier = Modifier
                                    .width(250.dp)
                                    .height(250.dp)


                            )
                            Spacer(Modifier.height(20.dp))
                            Text(
                                "Search here!",
                                modifier = Modifier.align(Alignment.CenterHorizontally),

                                )
                        }
                    }
                }

                if (viewModel.movieList.isEmpty() && !state.isLoading && (searchQuery != "" || searchQuery.isNotEmpty())) {

                    Box(modifier = Modifier.fillMaxSize()) {
                        Column(modifier = Modifier.align(Alignment.Center)) {
                            Image(
                                painter = painterResource(id = R.drawable.no_result_found),
                                contentDescription = null,
                                modifier = Modifier
                                    .width(250.dp)
                                    .height(250.dp)


                            )
                            Spacer(Modifier.height(20.dp))
                            Text(
                                "No Results Found!",
                                modifier = Modifier.align(Alignment.CenterHorizontally),

                                )
                        }
                    }
                }

                if (viewModel.movieList.isNotEmpty() && !state.isLoading)
                    LazyVerticalGrid(
                        state = gridState,
                        columns = GridCells.Adaptive(minSize = 100.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        itemsIndexed(viewModel.movieList) { index, item ->
                            MovieItem(url = item.medium_cover_image, Modifier.clickable {
                                navController.navigate(Screen.MovieDetailsScreen.route + "/${item.id}")
                            })
                        }
                    }
            }

            if (viewModel.movieList.isNotEmpty()) {
                gridState.OnBottomReached {
                    coroutineScope.launch {
                        if (searchQuery == "" || searchQuery.isEmpty())
                            viewModel.getMovies(query = searchQuery)
                    }

                }
            }
//            if (viewModel.movieList.value == null && !state.isLoading && state.error.isNotBlank()) {
//                Box(modifier = Modifier.fillMaxSize()) {
//                    Text(text = "No Results Found", modifier = Modifier.align(Alignment.Center))
//                }
//            }
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

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//private fun NoResultFound() {
//    Box(modifier = Modifier.fillMaxSize()) {
//        Column(modifier = Modifier.align(Alignment.Center)) {
//            Image(
//                painter = painterResource(id = R.drawable.no_result_found),
//                contentDescription = null,
//                modifier = Modifier
//                    .width(250.dp)
//                    .height(250.dp)
//
//
//            )
//            Spacer(Modifier.height(20.dp))
//            Text(
//                "No Results Found",
//                modifier = Modifier.align(Alignment.CenterHorizontally),
//
//                )
//        }
//    }
//}