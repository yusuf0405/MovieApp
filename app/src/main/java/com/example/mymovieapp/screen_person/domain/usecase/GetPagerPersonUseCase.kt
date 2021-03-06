package com.example.mymovieapp.screen_person.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mymovieapp.screen_person.data.source.PersonPageSource
import com.example.mymovieapp.screen_person.domain.models.Person
import com.example.mymovieapp.screen_person.domain.models.PersonResType
import com.example.mymovieapp.screen_person.domain.repository.PersonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPagerPersonUseCase @Inject constructor(private val repository: PersonRepository) {

    fun exesute(response: PersonResType, query: String): Flow<PagingData<Person>> {
        return Pager(
            config = PagingConfig(
                pageSize = 2,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                PersonPageSource(api = repository,
                    responseType = response,
                    query = query)
            }
        ).flow
    }
}