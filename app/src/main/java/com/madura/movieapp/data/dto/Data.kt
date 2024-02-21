package com.madura.movieapp.data.dto

data class Data(
    val limit: Int,
    val movie_count: Int,
    val movies: List<Movie>,
    val page_number: Int
)