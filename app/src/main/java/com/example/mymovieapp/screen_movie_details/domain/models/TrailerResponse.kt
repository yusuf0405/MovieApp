package com.example.mymovieapp.screen_movie_details.domain.models


import com.google.gson.annotations.SerializedName

data class TrailerResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("results") val trailerList: List<Trailer>,
)