package com.example.mymovieapp.person_details_screen.domain.model


import com.example.mymovieapp.movie_screen.domain.model.Movie
import com.google.gson.annotations.SerializedName

data class MovieCredits(
    @SerializedName("crew")
    var crew: List<Movie>,
    @SerializedName("id")
    var id: Int,
)