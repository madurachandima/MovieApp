package com.madura.movieapp.data.dto.movieDbDto

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["movieId"], unique = true)])
data class FavoriteMovieDto(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var movieId: Int = 0,
    var backgroundImage: String = "",
    var backgroundImageOriginal: String = "",
    var slug: String = "",
    var smallCoverImage: String = "",
    var title: String = "",
    var titleEnglish: String = "",
    var titleLong: String = "",
    var rating: String = "",
    var year: String = "",
    var runtime: String = "",
    var isWatched: Boolean = false,
    var isFavorite: Boolean = false,

    )