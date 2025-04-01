package com.madura.movieapp.presentation.homeScreen.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.madura.movieapp.presentation.Screen
import com.madura.movieapp.presentation.composible.MovieItem
import com.madura.movieapp.presentation.homeScreen.PopularMovieListState
import com.madura.movieapp.presentation.theme.white

@Composable
fun TrendingMedia(popularState: PopularMovieListState, navController: NavController) {

    Column {
        Text(
            modifier = Modifier.padding(top = 10.dp, start = 10.dp, bottom = 10.dp),
            text = "Trending ",
            textAlign = TextAlign.Left,
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 20.sp,
            color = white,
            fontWeight = FontWeight.SemiBold
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),

            verticalAlignment = Alignment.Top
        ) {
            items(popularState.sortedMovies) { sortedMovie ->
                MovieItem(url = sortedMovie.poster_path, Modifier.clickable {
                    navController.navigate(Screen.MovieDetailsScreen.route + "/${sortedMovie.id}")
                })
            }
        }
    }
}