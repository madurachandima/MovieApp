package com.madura.movieapp.presentation.homeScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.madura.movieapp.common.MediaType
import com.madura.movieapp.data.dto.tmdb.genre.Genre
import com.madura.movieapp.presentation.composible.MovieItem
import com.madura.movieapp.presentation.homeScreen.composable.MovieOrTv
import com.madura.movieapp.presentation.homeScreen.composable.TrendingMedia
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
    navController: NavController?,
    viewModel: HomeScreenViewModel? = hiltViewModel(),
) {

    val TAG = "HomeScreen"

    val popularState = viewModel!!.popularMovieState.value
    val genreMediaState = viewModel.mediaByGenreState.value

    val genreState = viewModel.genreState.value

    val gridState = rememberLazyGridState()


    val coroutineScope = rememberCoroutineScope()
    val mediaTypes = listOf("Movies", "Tv Series")

    var mediaType by remember { mutableStateOf(mediaTypes[0]) }

    var currentGenreList by remember { mutableStateOf(listOf<Genre>()) }

    var isInitialLoad by remember { mutableStateOf(false) }

    var selectedGenreId by remember {
        mutableIntStateOf(0)
    }

    LaunchedEffect(genreState.genres) {
        if (!isInitialLoad && viewModel.movieGenres.value.isNotEmpty()) {
            currentGenreList = viewModel.movieGenres.value
            isInitialLoad = true
            Log.d(
                TAG,
                "isInitialLoad HomeScreen LaunchedEffect: ${viewModel.movieGenres.value.size}"
            )
        }
    }

    fun getMediaByGenreId(mediaType: String, genreId: String) {
        coroutineScope.launch {
            viewModel.getMediaByGenreId(
                mediaType = mediaType,
                genreId = genreId
            )
        }
    }

    LaunchedEffect(mediaType) {
        if (viewModel.movieGenres.value.isNotEmpty() && viewModel.tvSeriesGenres.value.isNotEmpty()) {
            currentGenreList = if (mediaType == "Movies") {
                viewModel.movieGenres.value
            } else {
                viewModel.tvSeriesGenres.value
            }
            selectedGenreId = currentGenreList.first().id
            getMediaByGenreId(mediaType = mediaType, genreId = selectedGenreId.toString())
        }


    }



    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 8.dp, end = 8.dp, top = 8.dp)
            ) {
                TrendingMedia(popularState, navController!!)

                Spacer(modifier = Modifier.height(15.dp))

                if (!viewModel.genreState.value.isLoading) {
                    MovieOrTv(
                        mediaTypes = mediaTypes,
                        mediaType = mediaType
                    ) {
                        mediaType = it
                    }

                    Text(
                        modifier = Modifier.padding(top = 10.dp, start = 10.dp, bottom = 10.dp),
                        text = "$mediaType For You",
                        textAlign = TextAlign.Left,
                        style = MaterialTheme.typography.headlineLarge,
                        fontSize = 20.sp,
                        color = white,
                        fontWeight = FontWeight.SemiBold
                    )
                }


                if (!viewModel.genreState.value.isLoading && viewModel.genreState.value.genres != null && currentGenreList.isNotEmpty()) {

                    if (selectedGenreId == 0)
                        selectedGenreId = currentGenreList.first().id

                    LazyRow(modifier = Modifier.height(35.dp)) {
                        itemsIndexed(
                            currentGenreList
                        )
                        { index, genre ->
                            Box(
                                modifier = Modifier
                                    .padding(end = if (currentGenreList.size - 1 == index) 0.dp else 9.dp)
                                    .clip(shape = RoundedCornerShape(10.dp))
                                    .background(color = if (genre.id == selectedGenreId) red else darkPurple)
                                    .clickable {
                                        coroutineScope
                                            .launch {
                                                if (selectedGenreId == genre.id) return@launch

                                                selectedGenreId = genre.id
                                                if (mediaType == "Movies") {
                                                    viewModel.getMediaByGenreId(
                                                        mediaType = MediaType.movie.name,
                                                        genreId = selectedGenreId.toString()
                                                    )
                                                } else {
                                                    viewModel.getMediaByGenreId(
                                                        mediaType = MediaType.tv.name,
                                                        genreId = selectedGenreId.toString()
                                                    )
                                                }

                                            }
                                    }

                            ) {
                                Text(
                                    text = genre.name,
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
                }



                if (!genreMediaState.isLoading && viewModel.movieOrTvSeries.value != null && viewModel.movieOrTvSeries.value!!.isNotEmpty()) {
                    LazyVerticalGrid(
                        state = gridState,
                        columns = GridCells.Adaptive(minSize = 100.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        items(viewModel.movieOrTvSeries.value!!) { item ->
                            if (item?.poster_path != null)
                                MovieItem(url = item.poster_path ?: "", Modifier.clickable {
//                                navController?.navigate(Screen.MovieDetailsScreen.route + "/${sortedMovie.id}")
                                })
                        }

                    }
                }


            }




            if (!viewModel.movieOrTvSeries.value.isNullOrEmpty()) {
                gridState.OnBottomReached {
//                    if (mediaType == "Movies") {
//                        viewModel.getMediaByGenreId(
//                            mediaType = MediaType.movie.name,
//                            genreId = selectedGenreId.toString()
//                        )
//                    } else {
//                        viewModel.getMediaByGenreId(
//                            mediaType = MediaType.tv.name,
//                            genreId = selectedGenreId.toString()
//                        )
//                    }
                }
            }

            if (genreMediaState.error.isNotBlank() || genreState.error.isNotBlank() || popularState.error!!.isNotBlank()) {
                Text(
                    text = genreState.error.ifBlank {
                        genreMediaState.error.ifBlank {
                            popularState.error ?: ""
                        }
                    },
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

            if (popularState.isLoading || genreState.isLoading || genreMediaState.isLoading
            ) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }

}


