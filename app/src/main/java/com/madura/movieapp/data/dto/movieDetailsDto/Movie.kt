package com.madura.movieapp.data.dto.movieDetailsDto

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


data class Movie(
    var background_image: String?,
    var background_image_original: String?,
    var cast: List<Cast>?,
    var date_uploaded: String?,
    var date_uploaded_unix: Int?,
    var description_full: String?,
    var description_intro: String?,
    var genres: List<String>?,
    var id: Int?,
    var imdb_code: String?,
    var language: String?,
    var large_cover_image: String?,
    var large_screenshot_image1: String?,
    var large_screenshot_image2: String?,
    var large_screenshot_image3: String?,
    var like_count: Int?,
    var medium_cover_image: String?,
    var medium_screenshot_image1: String?,
    var medium_screenshot_image2: String?,
    var medium_screenshot_image3: String?,
    var mpa_rating: String?,
    var rating: Double?,
    var runtime: Int?,
    var slug: String?,
    var small_cover_image: String?,
    var title: String?,
    var title_english: String?,
    var title_long: String?,
    var torrents: List<Torrent>?,
    var url: String?,
    var year: Int?,
    var yt_trailer_code: String?,
    var isWatched: Boolean = false,
    var isFavorite: Boolean = false
) {
    override fun toString(): String {
        return "Movie(background_image='$background_image', background_image_original='$background_image_original', cast=$cast, date_uploaded='$date_uploaded', date_uploaded_unix=$date_uploaded_unix, description_full='$description_full', description_intro='$description_intro', genres=$genres, id=$id, imdb_code='$imdb_code', language='$language', large_cover_image='$large_cover_image', large_screenshot_image1='$large_screenshot_image1', large_screenshot_image2='$large_screenshot_image2', large_screenshot_image3='$large_screenshot_image3', like_count=$like_count, medium_cover_image='$medium_cover_image', medium_screenshot_image1='$medium_screenshot_image1', medium_screenshot_image2='$medium_screenshot_image2', medium_screenshot_image3='$medium_screenshot_image3', mpa_rating='$mpa_rating', rating=$rating, runtime=$runtime, slug='$slug', small_cover_image='$small_cover_image', title='$title', title_english='$title_english', title_long='$title_long', torrents=$torrents, url='$url', year=$year, yt_trailer_code='$yt_trailer_code')"
    }
}