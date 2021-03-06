package com.example.mymovieapp.screen_person.data.repository

import com.example.mymovieapp.app.api.PersonApi
import com.example.mymovieapp.screen_person.domain.models.PersonResponse
import com.example.mymovieapp.screen_person.domain.repository.PersonRepository
import retrofit2.Response
import javax.inject.Inject

class PersonRepositoryImpl @Inject constructor(private val personApi: PersonApi) :
    PersonRepository {
    override suspend fun getPopularPerson(page: Int, pageSize: Int): Response<PersonResponse> =
        personApi.getPopularPerson(page = page, pageSize = pageSize)

    override suspend fun getSearchPerson(
        query: String,
        page: Int,
        pageSize: Int,
    ): Response<PersonResponse> =
        personApi.getSearchPerson(query = query,
            page = page,
            pageSize = pageSize)

}