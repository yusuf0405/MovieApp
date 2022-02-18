package com.example.mymovieapp.person_details_screen.domain.repository

import com.example.mymovieapp.person_details_screen.domain.model.MovieCredits
import com.example.mymovieapp.person_details_screen.domain.model.PersonDetails
import retrofit2.Response

interface DetailsPersonRepository {

    suspend fun getPersonDetails(id: Int): Response<PersonDetails>

    suspend fun getPersonCreditMovies(id: Int): Response<MovieCredits>
}