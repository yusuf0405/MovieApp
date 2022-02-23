package com.example.mymovieapp.person_details_screen.domain.repository

import com.example.mymovieapp.person_details_screen.domain.models.MovieCredits
import com.example.mymovieapp.person_details_screen.domain.models.PersonDetails
import retrofit2.Response

interface DetailsPersonRepository {

    suspend fun getPersonDetails(id: Int): Response<PersonDetails>

    suspend fun getPersonCreditMovies(id: Int): Response<MovieCredits>
}