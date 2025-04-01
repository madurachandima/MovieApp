package com.madura.movieapp.data.dto.tmdb.trending

import com.google.gson.annotations.SerializedName

data class Trending(
    val page: Int,
    @SerializedName("results")
    val trendingResults: ArrayList<TrendingResult>,
    val total_pages: Int,
    val total_results: Int

) {
    override fun toString(): String {
        return "Trending(page=$page, trendingResults=$trendingResults, total_pages=$total_pages, total_results=$total_results)"
    }
}