package com.madura.movieapp.data.dto

import com.google.gson.annotations.SerializedName

data class MovieListDto(
    val data: Data,
    val status: String,
    @SerializedName("status_message")
    val statusMessage: String,
)