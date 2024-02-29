package com.madura.movieapp.data.dto

data class Data(
    val limit: Int,
    val movie_count: Int,
    val movies: ArrayList<Movie>,
    val page_number: Int
) {
    override fun toString(): String {
        return "Data(limit=$limit, movie_count=$movie_count, movies=$movies, page_number=$page_number)"
    }
}