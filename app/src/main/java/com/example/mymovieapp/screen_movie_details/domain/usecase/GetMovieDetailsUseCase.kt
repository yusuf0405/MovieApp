package com.example.mymovieapp.screen_movie_details.domain.usecase

import com.example.mymovieapp.screen_movie_details.domain.models.MovieDetails
import com.example.mymovieapp.screen_movie_details.domain.repository.DetailsMovieRepository
import retrofit2.Response
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(private val repository: DetailsMovieRepository) {
    suspend fun exesute(id: Int): Response<MovieDetails> = repository.getMovieDetails(id = id)
}