package com.madura.movieapp.data.dto.movieDetailsDto

data class Data(
    val movie: Movie
) {
    override fun toString(): String {
        return "Data(movie=$movie)"
    }
}