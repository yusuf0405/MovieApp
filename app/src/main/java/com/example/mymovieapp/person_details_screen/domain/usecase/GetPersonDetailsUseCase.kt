package com.example.mymovieapp.person_details_screen.domain.usecase

import com.example.mymovieapp.person_details_screen.domain.model.PersonDetails
import com.example.mymovieapp.person_details_screen.domain.repository.DetailsPersonRepository
import retrofit2.Response
import javax.inject.Inject

class GetPersonDetailsUseCase @Inject constructor(private val repository: DetailsPersonRepository) {
    suspend fun exesute(id: Int): Response<PersonDetails> = repository.getPersonDetails(id = id)
}