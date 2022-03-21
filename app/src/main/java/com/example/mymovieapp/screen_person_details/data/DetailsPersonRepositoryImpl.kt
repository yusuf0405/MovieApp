package com.example.mymovieapp.screen_person_details.data

import com.example.mymovieapp.app.api.PersonApi
import com.example.mymovieapp.screen_person_details.domain.models.MovieCredits
import com.example.mymovieapp.screen_person_details.domain.models.PersonDetails
import com.example.mymovieapp.screen_person_details.domain.repository.DetailsPersonRepository
import retrofit2.Response
import javax.inject.Inject

class DetailsPersonRepositoryImpl @Inject constructor(private val personApi: PersonApi) :
    DetailsPersonRepository {

    override suspend fun getPersonDetails(id: Int): Response<PersonDetails> =
        personApi.getPersonDetails(id = id)

    override suspend fun getPersonCreditMovies(id: Int): Response<MovieCredits> =
        personApi.getPersonCreditMovies(id = id)

}