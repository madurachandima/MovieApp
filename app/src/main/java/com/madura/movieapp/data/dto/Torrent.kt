package com.madura.movieapp.data.dto

data class Torrent(
    val audio_channels: String,
    val bit_depth: String,
    val date_uploaded: String,
    val date_uploaded_unix: Int,
    val hash: String,
    val is_repack: String,
    val peers: Int,
    val quality: String,
    val seeds: Int,
    val size: String,
    val size_bytes: Long,
    val type: String,
    val url: String,
    val video_codec: String
) {
    override fun toString(): String {
        return "Torrent(audio_channels='$audio_channels', bit_depth='$bit_depth', date_uploaded='$date_uploaded', date_uploaded_unix=$date_uploaded_unix, hash='$hash', is_repack='$is_repack', peers=$peers, quality='$quality', seeds=$seeds, size='$size', size_bytes=$size_bytes, type='$type', url='$url', video_codec='$video_codec')"
    }
}