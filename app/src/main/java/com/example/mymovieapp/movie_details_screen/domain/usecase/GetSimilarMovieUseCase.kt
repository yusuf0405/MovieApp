package com.example.mymovieapp.movie_details_screen.domain.usecase

import com.example.mymovieapp.movie_details_screen.domain.repository.DetailsMovieRepository
import com.example.mymovieapp.movie_screen.domain.model.MovieResponse
import retrofit2.Response
import javax.inject.Inject

class GetSimilarMovieUseCase @Inject constructor(private val repository: DetailsMovieRepository) {
    suspend fun exesute(id: Int): Response<MovieResponse> = repository.getSimilarMovie(id = id)
}