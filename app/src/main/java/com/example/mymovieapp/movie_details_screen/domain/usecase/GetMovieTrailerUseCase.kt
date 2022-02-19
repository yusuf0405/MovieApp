package com.example.mymovieapp.movie_details_screen.domain.usecase

import com.example.mymovieapp.movie_details_screen.domain.model.TrailerResponse
import com.example.mymovieapp.movie_details_screen.domain.repository.DetailsMovieRepository
import retrofit2.Response
import javax.inject.Inject

class GetMovieTrailerUseCase @Inject constructor(private val repository: DetailsMovieRepository) {
    suspend fun exesute(id: Int): Response<TrailerResponse> = repository.getMovieTrailer(id = id)
}