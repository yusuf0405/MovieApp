package com.example.mymovieapp.screen_person.domain.repository

import com.example.mymovieapp.screen_person.domain.models.PersonResponse
import retrofit2.Response

interface PersonRepository {
    suspend fun getPopularPerson(page: Int, pageSize: Int): Response<PersonResponse>
    suspend fun getSearchPerson(query: String, page: Int, pageSize: Int): Response<PersonResponse>
}