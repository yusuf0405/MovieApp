package com.example.mymovieapp.person_screen.domain.repository

import com.example.mymovieapp.person_screen.domain.models.PersonResponse
import retrofit2.Response

interface PersonRepository {
    suspend fun getPopularPerson(page: Int, pageSize: Int): Response<PersonResponse>
    suspend fun getSearchPerson(query: String, page: Int, pageSize: Int): Response<PersonResponse>
}