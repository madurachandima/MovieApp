package com.madura.movieapp.data.dto.movieListDto

data class Movie(
    val background_image: String,
    val background_image_original: String,
    val date_uploaded: String,
    val date_uploaded_unix: Int,
    val description_full: String,
    val genres: List<String>,
    val id: Int,
    val imdb_code: String,
    val language: String,
    val large_cover_image: String,
    val medium_cover_image: String,
    val mpa_rating: String,
    val rating: Double,
    val runtime: Int,
    val slug: String,
    val small_cover_image: String,
    val state: String,
    val summary: String,
    val synopsis: String,
    val title: String,
    val title_english: String,
    val title_long: String,
    val torrents: List<Torrent>,
    val url: String,
    val year: Int,
    val yt_trailer_code: String
) {
    override fun toString(): String {
        return "Movie(background_image='$background_image', background_image_original='$background_image_original', date_uploaded='$date_uploaded', date_uploaded_unix=$date_uploaded_unix, description_full='$description_full', genres=$genres, id=$id, imdb_code='$imdb_code', language='$language', large_cover_image='$large_cover_image', medium_cover_image='$medium_cover_image', mpa_rating='$mpa_rating', rating=$rating, runtime=$runtime, slug='$slug', small_cover_image='$small_cover_image', state='$state', summary='$summary', synopsis='$synopsis', title='$title', title_english='$title_english', title_long='$title_long', torrents=$torrents, url='$url', year=$year, yt_trailer_code='$yt_trailer_code')"
    }
}