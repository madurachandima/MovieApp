package com.madura.movieapp.data.dto.movieDetailsDto

data class MovieDetailsDto(

    val data: Data,
    val status: String,
    val status_message: String,
) {
    override fun toString(): String {
        return "MovieDetailsDto(data=$data, status='$status', status_message='$status_message')"
    }
}