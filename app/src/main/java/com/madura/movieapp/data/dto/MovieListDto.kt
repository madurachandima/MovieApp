package com.madura.movieapp.data.dto

import com.google.gson.annotations.SerializedName

data class MovieListDto(
    val data: Data? = null,
    val status: String? = null,
    @SerializedName("status_message")
    val statusMessage: String? = null,
)