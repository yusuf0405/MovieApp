package com.example.mymovieapp.screen_person_details.domain.repository

import com.example.mymovieapp.screen_person_details.domain.models.MovieCredits
import com.example.mymovieapp.screen_person_details.domain.models.PersonDetails
import retrofit2.Response

interface DetailsPersonRepository {

    suspend fun getPersonDetails(id: Int): Response<PersonDetails>

    suspend fun getPersonCreditMovies(id: Int): Response<MovieCredits>
}