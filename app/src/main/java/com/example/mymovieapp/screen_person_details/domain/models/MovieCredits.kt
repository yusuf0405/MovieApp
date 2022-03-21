package com.example.mymovieapp.screen_person_details.domain.models


import com.example.mymovieapp.screen_movie.domain.model.Movie
import com.google.gson.annotations.SerializedName

data class MovieCredits(
    @SerializedName("crew")
    var crew: List<Movie>,
    @SerializedName("id")
    var id: Int,
)