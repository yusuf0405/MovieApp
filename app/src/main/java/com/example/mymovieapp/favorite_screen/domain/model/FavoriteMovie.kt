package com.example.mymovieapp.favorite_screen.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favor_movie_database")
data class FavoriteMovie(
    @PrimaryKey val id: Int,
    val budget: Int,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val revenue: Long,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val title: String,
    val originalLanguage: String,
    val originalTitle: String,
    val voteCount: String,
    val video: Boolean,
    val rating: Double,
) : Serializable