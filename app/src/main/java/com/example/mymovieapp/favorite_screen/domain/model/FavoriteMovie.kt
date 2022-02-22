package com.example.mymovieapp.favorite_screen.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "favor_movie_database")
data class FavoriteMovie(
    @PrimaryKey val id: Int,
    val posterPath: String,
    val title: String,
    val rating: Double,
    val releaseDate: String,
) : Serializable