package com.example.mymovieapp.person_screen.presentation.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mymovieapp.person_screen.data.sourse.PersonPageSource
import com.example.mymovieapp.person_screen.domain.model.Person
import com.example.mymovieapp.person_screen.domain.repository.PersonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

enum class ResponsePersonType {
    PERSON,
    SEARCH

}

@ExperimentalCoroutinesApi
@HiltViewModel
class PersonViewModel @Inject constructor(
    private val repository: PersonRepository,
) : ViewModel() {
    private val _responseBy: MutableLiveData<ResponsePersonType> =
        MutableLiveData<ResponsePersonType>()
    private var query = ""


    val personFlow: Flow<PagingData<Person>> by lazy {
        _responseBy.asFlow()
            .flatMapLatest {
                getPagerMovies(it)
            }
            .cachedIn(viewModelScope)
    }

    private fun getPagerMovies(response: ResponsePersonType): Flow<PagingData<Person>> {
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

    fun responseType(newResponse: ResponsePersonType) {
        _responseBy.value = newResponse
    }

    fun responseSearchType(newQuery: String) {
        query = newQuery
        _responseBy.value = ResponsePersonType.SEARCH
    }

}