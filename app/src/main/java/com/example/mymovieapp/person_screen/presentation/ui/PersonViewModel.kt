package com.example.mymovieapp.person_screen.presentation.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mymovieapp.person_screen.domain.models.Person
import com.example.mymovieapp.person_screen.domain.models.ResponsePersonType
import com.example.mymovieapp.person_screen.domain.usecase.GetPagerPersonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject


@ExperimentalCoroutinesApi
@HiltViewModel
class PersonViewModel @Inject constructor(
    private val getPagerPersonUseCase: GetPagerPersonUseCase,
) : ViewModel() {

    private val _responseBy: MutableLiveData<ResponsePersonType> =
        MutableLiveData<ResponsePersonType>()
    private val _queryBy: MutableLiveData<String> = MutableLiveData<String>()

    init {
        _queryBy.value = ""
    }

    val personFlow: Flow<PagingData<Person>> by lazy {
        _responseBy.asFlow()
            .flatMapLatest {
                getPagerPersonUseCase.exesute(response = it, query = _queryBy.value!!)
            }
            .cachedIn(viewModelScope)
    }

    fun responseType(newResponse: ResponsePersonType) {
        _responseBy.value = newResponse
    }

    fun responseSearchType(newQuery: String) {
        _queryBy.value = newQuery
        _responseBy.value = ResponsePersonType.SEARCH
    }

}