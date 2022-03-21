package com.example.mymovieapp.screen_person.presentation.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mymovieapp.screen_person.domain.models.Person
import com.example.mymovieapp.screen_person.domain.models.PersonResType
import com.example.mymovieapp.screen_person.domain.usecase.GetPagerPersonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


@ExperimentalCoroutinesApi
@HiltViewModel
class PersonViewModel @Inject constructor(
    private val getPagerPersonUseCase: GetPagerPersonUseCase,
) : ViewModel() {

    private val _responseType: MutableLiveData<PersonResType> = MutableLiveData<PersonResType>()
    private val _queryBy: MutableLiveData<String> = MutableLiveData<String>()

    init {
        _queryBy.value = ""
    }

    val personFlow: Flow<PagingData<Person>> by lazy {
        _responseType.asFlow()
            .flatMapLatest {
                getPagerPersonUseCase.exesute(response = it, query = _queryBy.value!!)
            }
            .cachedIn(viewModelScope)
            .flowOn(Dispatchers.IO)
    }

    fun responseType(newResponse: PersonResType) {
        _responseType.value = newResponse
    }

    fun responseSearchType(newQuery: String) {
        _queryBy.value = newQuery
        _responseType.value = PersonResType.SEARCH
    }

}