package com.madura.movieapp.presentation.favorite_movies_screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.madura.movieapp.R
import com.madura.movieapp.presentation.composible.MovieItem
import com.madura.movieapp.presentation.theme.white
import kotlinx.coroutines.launch

@Composable
fun FavoriteMovieScreen(
    navController: NavController,
    viewModel: FavoriteMovieScreenViewModel = hiltViewModel(),
) {

    val TAG = "FavoriteMovieScreen"

    val state = viewModel.state.value
    val updateMovieState = viewModel.updateFavoriteMovieState.value
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var snackbarState by remember { mutableStateOf(false) }
    var snackBarMessage by remember { mutableStateOf("") }
    var snackBarColor by remember { mutableStateOf(Color.Green) }

//    LaunchedEffect(updateMovieState) {
//        if (updateMovieState.movieId != null && updateMovieState.movieId > 0) {
//            snackbarState = true
//            snackBarMessage = "Movie removed from favorites"
//            snackBarColor = Color.Green
//        }
//        if (updateMovieState.error.isNotBlank()) {
//            snackbarState = true
//            snackBarMessage = updateMovieState.error
//            snackBarColor = Color.Red
//        }
//    }

//    LaunchedEffect(snackbarState) {
//        if (snackbarState) {
//            snackbarHostState.showSnackbar(
//                message = snackBarMessage,
//                duration = SnackbarDuration.Short
//            )
//            snackbarState = false
//        }
//    }

    LaunchedEffect(key1 = Unit) {
        viewModel.getFavoriteMovies()
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }

    )
    { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            Column {
                Text(
                    modifier = Modifier.padding(top = 10.dp, start = 10.dp, bottom = 10.dp),
                    text = "Your favorite movies here",
                    textAlign = TextAlign.Left,
                    style = MaterialTheme.typography.headlineLarge,
                    fontSize = 20.sp,
                    color = white, fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.height(10.dp))

                if (state.isLoading || updateMovieState.isLoading) {
                    CircularProgressIndicator()
                } else if (state.movies.isNullOrEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(text = "No movies found", color = white, textAlign = TextAlign.Center)
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {

                        items(count = state.movies.size) {

                            val movie = state.movies[it]

                            Row(modifier = Modifier.fillMaxWidth()) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .width(160.dp)
                                        .height(260.dp)
                                        .clip(shape = RoundedCornerShape(20.dp))
                                ) {
                                    MovieItem(
                                        url = movie.backgroundImage,
                                        modifier = Modifier,
                                        radius = 20.dp
                                    )
                                }
                                Spacer(modifier = Modifier.width(15.dp))
                                Column(
                                    horizontalAlignment = Alignment.Start,
                                    modifier = Modifier.padding(top = 20.dp)
                                ) {
                                    Text(
                                        text = movie.titleEnglish,
                                        style = MaterialTheme.typography.headlineLarge,
                                        fontSize = 22.sp,
                                        color = white,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Row(
                                        horizontalArrangement = Arrangement.Start,
                                        verticalAlignment = Alignment.CenterVertically,

                                        ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.star),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .height(15.dp)
                                                .width(15.dp)
                                        )
                                        Text(
                                            text = movie.rating,
                                            fontSize = 16.sp,
                                            color = white,
                                            fontWeight = FontWeight.Normal,
                                            modifier = Modifier.padding(horizontal = 2.dp)
                                        )
                                        Image(
                                            painter = painterResource(id = R.drawable.imdb),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .height(25.dp)

                                        )
                                    }

                                    Spacer(modifier = Modifier.height(10.dp))
                                    Row {
                                        Text(
                                            text = movie.year,
                                            fontSize = 14.sp,
                                            color = white,
                                            fontWeight = FontWeight.W500,

                                            )
                                        Text(
                                            text = "|",
                                            fontSize = 14.sp,
                                            color = white,
                                            fontWeight = FontWeight.W500,
                                            modifier = Modifier.padding(horizontal = 2.dp),

                                            )


                                        Text(
                                            text = movie.runtime,
                                            fontSize = 14.sp,
                                            color = white,
                                            fontWeight = FontWeight.W500,
                                        )

                                    }

                                    Row(
                                        modifier = Modifier,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        IconButton(
                                            onClick = {
                                                Log.d(TAG, "FavoriteMovieScreen: remove movie call")

                                                coroutineScope.launch {
                                                    viewModel.updateFavoriteMovieStatus(
                                                        movie.movieId,
                                                        isFavorite = false
                                                    )
                                                }


                                            }) {
                                            Icon(
                                                imageVector = Icons.Rounded.Favorite,
                                                contentDescription = null,
                                                tint = Color.Red
                                            )
                                        }
                                        IconButton(
                                            onClick = {

                                            }) {
                                            Icon(
                                                imageVector = Icons.Rounded.AddCircle,
                                                contentDescription = null
                                            )
                                        }
                                    }

                                }

                            }
                        }


                    }

                }
            }

        }
    }
}
