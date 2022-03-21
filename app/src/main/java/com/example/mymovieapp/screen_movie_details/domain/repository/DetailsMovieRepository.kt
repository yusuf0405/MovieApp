package com.example.mymovieapp.screen_movie_details.domain.repository

import com.example.mymovieapp.screen_movie_details.domain.models.MovieDetails
import com.example.mymovieapp.screen_movie_details.domain.models.TrailerResponse
import com.example.mymovieapp.screen_movie.domain.model.MovieResponse
import retrofit2.Response

interface DetailsMovieRepository {

    suspend fun getMovieDetails(id: Int): Response<MovieDetails>

    suspend fun getMovieTrailer(id: Int): Response<TrailerResponse>

    suspend fun getSimilarMovie(id: Int): Response<MovieResponse>
}