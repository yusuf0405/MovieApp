package com.example.mymovieapp.person_details_screen.data.repository

import com.example.mymovieapp.app_network.RetrofitInstance
import com.example.mymovieapp.person_details_screen.domain.model.MovieCredits
import com.example.mymovieapp.person_details_screen.domain.model.PersonDetails
import com.example.mymovieapp.person_details_screen.domain.repository.DetailsPersonRepository
import retrofit2.Response

class DetailsPersonRepositoryImpl : DetailsPersonRepository {
    override suspend fun getPersonDetails(id: Int): Response<PersonDetails> {
        return RetrofitInstance.personApi.getPersonDetails(id = id)
    }

    override suspend fun getPersonCreditMovies(id: Int): Response<MovieCredits> {
        return RetrofitInstance.personApi.getPersonCreditMovies(id = id)
    }
}