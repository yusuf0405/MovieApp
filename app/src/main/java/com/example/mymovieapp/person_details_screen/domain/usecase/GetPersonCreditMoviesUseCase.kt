package com.example.mymovieapp.person_details_screen.domain.usecase

import com.example.mymovieapp.person_details_screen.domain.models.MovieCredits
import com.example.mymovieapp.person_details_screen.domain.repository.DetailsPersonRepository
import retrofit2.Response
import javax.inject.Inject

class GetPersonCreditMoviesUseCase @Inject constructor(private val repository: DetailsPersonRepository) {
    suspend fun exesute(id: Int): Response<MovieCredits> = repository.getPersonCreditMovies(id = id)
}