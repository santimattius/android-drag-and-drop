package com.santimattius.android.compose.feature.main.data

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.json.*

@Serializable
data class MovieResponse(
    val page: Long,
    val results: List<Movie>,
    @SerialName("total_pages") val totalPages: Long,
    @SerialName("total_results") val totalResults: Long,
)

@Serializable
data class Movie(
    val id: Long,
    val adult: Boolean,
    @SerialName("backdrop_path") val backdropPath: String,
    @SerialName("genre_ids") val genreIDS: List<Long>,
    @SerialName("original_title") val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerialName("poster_path") val posterPath: String,
    @SerialName("release_date") val releaseDate: String,
    val title: String,
    val video: Boolean,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("vote_count") val voteCount: Long,
){
    val poster: String
        get() = "https://image.tmdb.org/t/p/w500${posterPath}"
}
