package com.madura.movieapp.presentation.homeScreen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.madura.movieapp.data.dto.Movie
import com.madura.movieapp.ui.composable.OnBottomReached
import com.madura.movieapp.ui.composable.OnBottomReached as On


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
) {

    val TAG = "HomeScreen"
    val state = viewModel.state.value

    val gridState = rememberLazyGridState()


    Box(modifier = Modifier.fillMaxSize()) {

        if (viewModel.movieList.value.isNotEmpty()) {
            LazyVerticalGrid(
                state = gridState,
                columns = GridCells.Adaptive(minSize = 100.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                itemsIndexed(viewModel.movieList.value) { index, item ->
                    ImageItem(item)
                }
//                if (state.isPaginationLoading)
//                    item(span = { GridItemSpan(3) }) {
//                        LinearProgressIndicator(
//                            modifier = Modifier.fillMaxWidth(),
//                            color = MaterialTheme.colorScheme.surfaceTint
//                        )
//                    }
            }
        }

        if(viewModel.movieList.value.isNotEmpty()){
            gridState.OnBottomReached {
                viewModel.getMovies()
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


@Composable
fun ImageItem(move: Movie) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(8.dp)
            .clip(shape = RoundedCornerShape(10.dp))
    ) {
        AsyncImage(
            model = move.medium_cover_image,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxSize()
        )
    }
}