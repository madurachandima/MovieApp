package com.madura.movieapp.presentation.screens.theme.homeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage



val photoList = listOf<String>(
    "https://img.yts.mx/assets/images/movies/timescape_2022/medium-cover.jpg",
    "https://img.yts.mx/assets/images/movies/timescape_2022/medium-cover.jpg",
    "https://img.yts.mx/assets/images/movies/timescape_2022/medium-cover.jpg",
    "https://img.yts.mx/assets/images/movies/timescape_2022/medium-cover.jpg",
    "https://img.yts.mx/assets/images/movies/timescape_2022/medium-cover.jpg",
    "https://img.yts.mx/assets/images/movies/timescape_2022/medium-cover.jpg",
    "https://img.yts.mx/assets/images/movies/timescape_2022/medium-cover.jpg",
    "https://img.yts.mx/assets/images/movies/timescape_2022/medium-cover.jpg",
    "https://img.yts.mx/assets/images/movies/timescape_2022/medium-cover.jpg",
    "https://img.yts.mx/assets/images/movies/timescape_2022/medium-cover.jpg",
    "https://img.yts.mx/assets/images/movies/timescape_2022/medium-cover.jpg",
    "https://img.yts.mx/assets/images/movies/timescape_2022/medium-cover.jpg",
    "https://img.yts.mx/assets/images/movies/timescape_2022/medium-cover.jpg",
    "https://img.yts.mx/assets/images/movies/timescape_2022/medium-cover.jpg",
    "https://img.yts.mx/assets/images/movies/timescape_2022/medium-cover.jpg",
    "https://img.yts.mx/assets/images/movies/timescape_2022/medium-cover.jpg",
    "https://img.yts.mx/assets/images/movies/timescape_2022/medium-cover.jpg",
    "https://img.yts.mx/assets/images/movies/timescape_2022/medium-cover.jpg",
    "https://img.yts.mx/assets/images/movies/timescape_2022/medium-cover.jpg",
    "https://img.yts.mx/assets/images/movies/timescape_2022/medium-cover.jpg",
)


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 100.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        items(photoList) { item ->
            ImageItem(item)
        }
    }
}


@Composable
fun ImageItem(item:String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(8.dp)
            .clip(shape = RoundedCornerShape(10.dp))
    ) {
        AsyncImage(
            model = item,
            contentDescription = null,
            contentScale= ContentScale.FillWidth,
            modifier = Modifier.fillMaxSize()
        )
    }
}