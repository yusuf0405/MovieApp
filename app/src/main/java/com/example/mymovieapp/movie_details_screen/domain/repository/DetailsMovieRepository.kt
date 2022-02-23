package com.example.mymovieapp.movie_details_screen.domain.repository

import com.example.mymovieapp.movie_details_screen.domain.models.MovieDetails
import com.example.mymovieapp.movie_details_screen.domain.models.TrailerResponse
import com.example.mymovieapp.movie_screen.domain.model.MovieResponse
import retrofit2.Response

interface DetailsMovieRepository {

    suspend fun getMovieDetails(id: Int): Response<MovieDetails>

    suspend fun getMovieTrailer(id: Int): Response<TrailerResponse>

    suspend fun getSimilarMovie(id: Int): Response<MovieResponse>
}