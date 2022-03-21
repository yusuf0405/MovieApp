package com.example.mymovieapp.screen_movie.presentation.ui

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mymovieapp.screen_movie.domain.model.Movie
import com.example.mymovieapp.screen_movie.domain.model.ResponseUser
import com.example.mymovieapp.screen_movie.domain.usecase.GetPagerMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getPagerMoviesUseCase: GetPagerMoviesUseCase,
) : ViewModel() {

    private val _responseType: MutableLiveData<ResponseUser> = MutableLiveData<ResponseUser>()
    private val _queryBy: MutableLiveData<String> = MutableLiveData<String>()

    private val _lastResponse: MutableLiveData<ResponseUser> = MutableLiveData<ResponseUser>()
    val lastResponse: LiveData<ResponseUser> = _lastResponse


    init { _queryBy.value = "" }

    val movieFlow: Flow<PagingData<Movie>> by lazy(LazyThreadSafetyMode.NONE) {
        _responseType.asFlow()
            .flatMapLatest {
                getPagerMoviesUseCase.exesute(response = it, query = _queryBy.value!!)
            }
            .cachedIn(viewModelScope)
            .flowOn(Dispatchers.IO)
    }

    fun responseType(newResponse: ResponseUser) {
        _lastResponse.value = newResponse
        _responseType.value = newResponse
    }

    fun responseSearchType(newQuery: String) {
        _queryBy.value = newQuery
        if (_responseType.value != ResponseUser.SEARCH) _lastResponse.value = _responseType.value
        _responseType.value = ResponseUser.SEARCH
    }
}