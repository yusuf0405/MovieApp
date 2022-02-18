package com.example.mymovieapp.person_screen.data.repository

import com.example.mymovieapp.app_network.RetrofitInstance
import com.example.mymovieapp.person_screen.domain.model.PersonResponse
import com.example.mymovieapp.person_screen.domain.repository.PersonRepository
import retrofit2.Response

class PersonRepositoryImpl : PersonRepository {
    override suspend fun getPopularPerson(page: Int, pageSize: Int): Response<PersonResponse> {
        return RetrofitInstance.personApi.getPopularPerson(page = page, pageSize = pageSize)
    }
    override suspend fun getSearchPerson(
        query: String,
        page: Int,
        pageSize: Int,
    ): Response<PersonResponse> {
        return RetrofitInstance.personApi.getSearchPerson(query = query,
            page = page,
            pageSize = pageSize)
    }
}