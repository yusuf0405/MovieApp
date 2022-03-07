package com.example.mymovieapp.movie_screen.presentation.ui

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mymovieapp.movie_screen.domain.model.Movie
import com.example.mymovieapp.movie_screen.domain.model.ResponseUser
import com.example.mymovieapp.movie_screen.domain.usecase.GetPagerMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getPagerMoviesUseCase: GetPagerMoviesUseCase,
) : ViewModel() {

    private val _responseType: MutableLiveData<ResponseUser> = MutableLiveData<ResponseUser>()
    private val _responseOldType: MutableLiveData<ResponseUser> = MutableLiveData<ResponseUser>()
    val responseType: LiveData<ResponseUser> = _responseOldType

    private val _queryBy: MutableLiveData<String> = MutableLiveData<String>()

    init { _queryBy.value = "" }

    val movieFlow: Flow<PagingData<Movie>> by lazy(LazyThreadSafetyMode.NONE) {
        _responseType.asFlow()
            .flatMapLatest {
                getPagerMoviesUseCase.exesute(response = it, query = _queryBy.value!!)
            }
            .cachedIn(viewModelScope)
    }

    fun responseType(newResponse: ResponseUser) {
        _responseOldType.value = newResponse
        _responseType.value = newResponse
    }

    fun responseSearchType(newQuery: String) {
        _queryBy.value = newQuery
        if (_responseType.value != ResponseUser.SEARCH) _responseOldType.value = _responseType.value
        _responseType.value = ResponseUser.SEARCH
    }
}