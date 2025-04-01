package com.madura.movieapp.data.dto.tmdb.mediaByGenre

data class MediaByGenre(
    val page: Int?,
    val results: ArrayList<Result?>?,
    val total_pages: Int?,
    val total_results: Int?
)