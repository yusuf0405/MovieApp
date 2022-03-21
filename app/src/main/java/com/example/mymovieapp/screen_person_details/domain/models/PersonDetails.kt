package com.example.mymovieapp.screen_person_details.domain.models


import com.google.gson.annotations.SerializedName

data class PersonDetails(
    @SerializedName("adult")
    var adult: Boolean,
    @SerializedName("biography")
    var biography: String,
    @SerializedName("birthday")
    var birthday: String,
    @SerializedName("gender")
    var gender: Int,
    @SerializedName("id")
    var id: Int,
    @SerializedName("imdb_id")
    var imdbId: String,
    @SerializedName("known_for_department")
    var knownForDepartment: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("place_of_birth")
    var placeOfBirth: String,
    @SerializedName("popularity")
    var popularity: Double,
    @SerializedName("profile_path")
    var profilePath: String,
)