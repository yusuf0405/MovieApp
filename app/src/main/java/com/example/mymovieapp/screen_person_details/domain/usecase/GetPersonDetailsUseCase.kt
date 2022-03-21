package com.example.mymovieapp.screen_person_details.domain.usecase

import com.example.mymovieapp.screen_person_details.domain.models.PersonDetails
import com.example.mymovieapp.screen_person_details.domain.repository.DetailsPersonRepository
import retrofit2.Response
import javax.inject.Inject

class GetPersonDetailsUseCase @Inject constructor(private val repository: DetailsPersonRepository) {
    suspend fun exesute(id: Int): Response<PersonDetails> = repository.getPersonDetails(id = id)
}