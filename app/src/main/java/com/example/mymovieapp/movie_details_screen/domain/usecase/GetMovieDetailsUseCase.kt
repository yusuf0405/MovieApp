package com.example.mymovieapp.movie_details_screen.domain.usecase

import com.example.mymovieapp.movie_details_screen.domain.model.MovieDetails
import com.example.mymovieapp.movie_details_screen.domain.repository.DetailsMovieRepository
import retrofit2.Response
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(private val repository: DetailsMovieRepository) {
    suspend fun exesute(id: Int): Response<MovieDetails> = repository.getMovieDetails(id = id)
}