package com.madura.movieapp.data.dto

data class Data(
    val limit: Int,
    val movie_count: Int,
    val movies: ArrayList<Movie>,
    val page_number: Int
)