package com.example.mymovieapp.screen_movie_details.domain.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MovieDetails(
    val budget: Int,
    val id: Int,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val revenue: Long,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val title: String,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("vote_count")
    val voteCount: String,
    val video: Boolean,
    @SerializedName("vote_average")
    val rating: Double,
) : Serializable