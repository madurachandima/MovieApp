package com.madura.movieapp.data.dto.movieListDto

import com.google.gson.annotations.SerializedName

data class MovieListDto(
    val data: Data? = null,
    val status: String? = null,
    @SerializedName("status_message")
    val statusMessage: String? = null,
) {
    override fun toString(): String {
        return "MovieListDto(data=$data, status=$status, statusMessage=$statusMessage)"
    }
}