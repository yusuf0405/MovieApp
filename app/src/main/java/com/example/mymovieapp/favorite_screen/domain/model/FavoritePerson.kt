package com.example.mymovieapp.favorite_screen.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favor_person_database")
data class FavoritePerson(
    @PrimaryKey var id: Int,
    var adult: Boolean,
    var biography: String,
    var birthday: String,
    var gender: Int,
    var imdbId: String,
    var knownForDepartment: String,
    var name: String,
    var placeOfBirth: String,
    var popularity: Double,
    var profilePath: String,
)