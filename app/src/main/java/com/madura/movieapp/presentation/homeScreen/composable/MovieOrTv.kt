package com.madura.movieapp.presentation.homeScreen.composable

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text


import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.madura.movieapp.presentation.theme.btnColor
import com.madura.movieapp.presentation.theme.darkPurple

@Composable
fun MovieOrTv(mediaTypes: List<String>, mediaType: String, onClick: (String) -> Unit = {}) {

    LazyRow {
        items(mediaTypes) {
            MediaType(title = it, selected = it == mediaType) {
                onClick(it)
            }
        }
    }
}

@Composable
private fun MediaType(title: String, selected: Boolean = false, onClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .width(110.dp)
            .padding(end = 5.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(if (selected) btnColor else Color.Transparent)
            .border(shape = RoundedCornerShape(20.dp), color = darkPurple, width = 2.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center

    ) {
        Text(
            title,
            style = TextStyle(color = Color.White),
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}
