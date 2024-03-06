package com.madura.movieapp.presentation.movieDetailsScreen

import android.graphics.drawable.PaintDrawable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.madura.movieapp.R
import com.madura.movieapp.common.ExpandedText
import com.madura.movieapp.data.dto.movieDetailsDto.Cast
import com.madura.movieapp.presentation.Screen
import com.madura.movieapp.presentation.theme.red
import com.madura.movieapp.presentation.theme.white
import java.util.concurrent.TimeUnit

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MovieDetailsScreen(
    viewModel: MovieDetailsScreenViewModel = hiltViewModel(),
) {
    val TAG = "MovieDetailsScreen"

    val state = viewModel.movieDetailsState.value

    val coroutineScope = rememberCoroutineScope()

    fun fromMinutesToHHmm(minutes: Int): String {
        val hours = TimeUnit.MINUTES.toHours(minutes.toLong())
        val remainMinutes = minutes - TimeUnit.HOURS.toMinutes(hours)
        return String.format("%02d:%02d", hours, remainMinutes)
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp, start = 8.dp, end = 8.dp)
    ) {


        if (state.movieDetails != null)
            LazyColumn(
                modifier = Modifier,
                state = rememberLazyListState()
            ) {
                item {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .width(180.dp)
                                .height(280.dp)
                                .clip(shape = RoundedCornerShape(20.dp))
                        ) {
                            AsyncImage(
                                model = state.movieDetails.medium_cover_image,
                                contentDescription = null,
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(shape = RoundedCornerShape(20.dp))
                            )
                        }
                        Spacer(modifier = Modifier.width(15.dp))
                        Column(
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier.padding(top = 20.dp)
                        ) {
                            Text(
                                text = state.movieDetails.title_english ?: "_",
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
                                    text = if (state.movieDetails.rating == null) "_" else state.movieDetails.rating.toString(),
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
                                    text = if (state.movieDetails.year == null) "_" else state.movieDetails.year.toString(),
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

                                if (state.movieDetails.runtime == null)
                                    Text(
                                        text = "_",
                                        fontSize = 14.sp,
                                        color = white,
                                        fontWeight = FontWeight.W500,
                                    )
                                else
                                    Text(
                                        text = "${
                                            fromMinutesToHHmm(state.movieDetails.runtime).split(":")
                                                .first()
                                        }h  ${
                                            fromMinutesToHHmm(state.movieDetails.runtime).split(":")
                                                .last()
                                        }min",
                                        fontSize = 14.sp,
                                        color = white,
                                        fontWeight = FontWeight.W500,
                                    )
                            }

                        }

                    }
                }

                item {
                    if (state.movieDetails.description_intro == null)
                        Text(
                            text = "_",
                            fontSize = 14.sp,
                            color = white,
                            fontWeight = FontWeight.W500,
                        )
                    else if (state.movieDetails.description_intro.length < 499)
                        Text(
                            text = state.movieDetails.description_intro,
                            fontSize = 14.sp,
                            color = white,
                            fontWeight = FontWeight.W500,
                        )
                    else
                        ExpandedText(
                            text = state.movieDetails.description_intro.substring(0, 500),
                            expandedText = state.movieDetails.description_intro,
                            expandedTextButton = "See more",
                            shrinkTextButton = "Less",
                            textStyle = TextStyle(
                                fontSize = 16.sp,
                                color = white,
                                fontWeight = FontWeight.Normal,
                            ),
                            expandedTextStyle = TextStyle(
                                fontSize = 16.sp,
                                color = white, fontWeight = FontWeight.Normal,
                            ),
                            expandedTextButtonStyle = TextStyle(
                                fontSize = 16.sp,
                                color = red, fontWeight = FontWeight.Normal,
                            ),
                            shrinkTextButtonStyle = TextStyle(
                                fontSize = 16.sp,
                                color = red, fontWeight = FontWeight.Normal,
                            )
                        )
                }

                if (state.movieDetails.cast != null)
                    item {
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp)
                        ) {
                            items(state.movieDetails.cast) { cast ->
                                ActorCard(cast)
                            }

                        }
                    }
            }

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
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
    }

}


@Composable
fun ActorCard(cast: Cast) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        AsyncImage(
            model = cast.url_small_image ?: "",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
        )

        Column(modifier = Modifier.padding(start = 5.dp)) {
            Text(
                text = cast.name ?: "_",
                fontSize = 14.sp,
                color = white,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 2.dp)
            )
            Text(
                text = cast.character_name ?: "_",
                fontSize = 14.sp,
                color = white,
                fontWeight = FontWeight.W300,
                modifier = Modifier.padding(horizontal = 2.dp)
            )
        }
    }
}
