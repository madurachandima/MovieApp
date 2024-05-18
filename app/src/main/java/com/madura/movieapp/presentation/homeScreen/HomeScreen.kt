package com.madura.movieapp.presentation.homeScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.madura.movieapp.common.Category.movieGenre
import com.madura.movieapp.presentation.Screen
import com.madura.movieapp.presentation.composible.MovieItem
import com.madura.movieapp.presentation.theme.darkPurple
import com.madura.movieapp.presentation.theme.red
import com.madura.movieapp.presentation.theme.white
import com.madura.movieapp.ui.composable.OnBottomReached
import kotlinx.coroutines.launch


data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val route: String,
)

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel(),
) {

    val TAG = "HomeScreen"
    val state = viewModel.state.value
    val popularState = viewModel.popularMovieState.value

    val gridState = rememberLazyGridState()

    val selectedGenre = remember {
        mutableStateOf("All")
    }

    val coroutineScope = rememberCoroutineScope()


    Scaffold(
        modifier = Modifier.fillMaxSize(),
    )
    { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            if (popularState.sortedMovies.isNotEmpty() && viewModel.movieList.value.isNotEmpty())
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 8.dp, end = 8.dp, top = 8.dp)
                )
                {

                    Text(
                        modifier = Modifier.padding(top = 10.dp, start = 10.dp, bottom = 10.dp),
                        text = "Popular Downloads",
                        textAlign = TextAlign.Left,
                        style = MaterialTheme.typography.headlineLarge,
                        fontSize = 20.sp,
                        color = white, fontWeight = FontWeight.SemiBold
                    )

                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),

                        verticalAlignment = Alignment.Top
                    ) {
                        items(popularState.sortedMovies) { sortedMovie ->
                            MovieItem(url = sortedMovie.medium_cover_image, Modifier.clickable {
                                navController.navigate(Screen.MovieDetailsScreen.route + "/${sortedMovie.id}")
                            })
                        }
                    }


                    Text(
                        modifier = Modifier.padding(top = 10.dp, start = 10.dp, bottom = 10.dp),
                        text = "Movies For You",
                        textAlign = TextAlign.Left,
                        style = MaterialTheme.typography.headlineLarge,
                        fontSize = 20.sp,
                        color = white, fontWeight = FontWeight.SemiBold
                    )

                    LazyRow(modifier = Modifier.height(35.dp)) {
                        itemsIndexed(movieGenre) { index, genre ->
                            val isSelected = genre == selectedGenre.value
                            Box(
                                modifier = Modifier
                                    .padding(end = if (movieGenre.size - 1 == index) 0.dp else 9.dp)
                                    .clip(shape = RoundedCornerShape(10.dp))
                                    .background(color = if (isSelected) red else darkPurple)
                                    .clickable {
                                        coroutineScope
                                            .launch {
                                                selectedGenre.value = genre
                                                Log.d(
                                                    TAG,
                                                    "selected value = ${selectedGenre.value}"
                                                )
                                                viewModel.getMovies(
                                                    genre = if (selectedGenre.value == "All") null else selectedGenre.value,
                                                    resetPage = true
                                                )
                                            }


                                    }

                            ) {
                                Text(
                                    text = genre,
                                    modifier = Modifier.padding(
                                        top = 4.dp,
                                        bottom = 4.dp,
                                        start = 10.dp,
                                        end = 10.dp
                                    ),
                                    textAlign = TextAlign.Center,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.W500
                                )
                            }
                        }

                    }

                    if (!state.isLoading)
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
                    viewModel.getMovies()
                }
            }

            if (popularState.error.isNotBlank() || state.error.isNotBlank()) {
                Text(
                    text = state.error.ifBlank { popularState.error },
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

            if (popularState.isLoading || state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }

}
