package com.example.mymovieapp.person_details_screen.data.repository

import com.example.mymovieapp.app.api.PersonApi
import com.example.mymovieapp.person_details_screen.domain.models.MovieCredits
import com.example.mymovieapp.person_details_screen.domain.models.PersonDetails
import com.example.mymovieapp.person_details_screen.domain.repository.DetailsPersonRepository
import retrofit2.Response
import javax.inject.Inject

class DetailsPersonRepositoryImpl @Inject constructor(private val personApi: PersonApi) :
    DetailsPersonRepository {
    override suspend fun getPersonDetails(id: Int): Response<PersonDetails> =
        personApi.getPersonDetails(id = id)

    override suspend fun getPersonCreditMovies(id: Int): Response<MovieCredits> =
        personApi.getPersonCreditMovies(id = id)

}