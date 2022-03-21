package com.example.mymovieapp.screen_person_details.domain.usecase

import com.example.mymovieapp.screen_person_details.domain.models.MovieCredits
import com.example.mymovieapp.screen_person_details.domain.repository.DetailsPersonRepository
import retrofit2.Response
import javax.inject.Inject

class GetPersonCreditMoviesUseCase @Inject constructor(private val repository: DetailsPersonRepository) {
    suspend fun exesute(id: Int): Response<MovieCredits> = repository.getPersonCreditMovies(id = id)
}