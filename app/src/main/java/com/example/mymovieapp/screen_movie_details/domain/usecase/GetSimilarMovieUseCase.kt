package com.example.mymovieapp.screen_movie_details.domain.usecase

import com.example.mymovieapp.screen_movie_details.domain.repository.DetailsMovieRepository
import com.example.mymovieapp.screen_movie.domain.model.MovieResponse
import retrofit2.Response
import javax.inject.Inject

class GetSimilarMovieUseCase @Inject constructor(private val repository: DetailsMovieRepository) {
    suspend fun exesute(id: Int): Response<MovieResponse> = repository.getSimilarMovie(id = id)
}