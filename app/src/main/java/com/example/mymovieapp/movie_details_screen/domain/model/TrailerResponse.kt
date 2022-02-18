package com.example.mymovieapp.movie_details_screen.domain.model


import com.google.gson.annotations.SerializedName

data class TrailerResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val trailerList: List<Trailer>,
)