package com.example.mymovieapp.person_screen.domain.models


import com.google.gson.annotations.SerializedName

data class Person(
    @SerializedName("adult")
    val adult: Boolean,
    @SerializedName("id")
    val id: Int,
    @SerializedName("biography")
    val biography: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("profile_path")
    val profile: String,
)