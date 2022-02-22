package com.example.mymovieapp.movie_screen.presentation.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mymovieapp.favorite_screen.domain.usecase.AddMovieFavoriteUseCase
import com.example.mymovieapp.favorite_screen.domain.usecase.AllFavoriteMoviesUseCase
import com.example.mymovieapp.favorite_screen.domain.usecase.DeleteMovieFavoriteUseCase
import com.example.mymovieapp.movie_screen.domain.model.Movie
import com.example.mymovieapp.movie_screen.domain.model.ResponseUser
import com.example.mymovieapp.movie_screen.domain.usecase.GetPagerMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@FlowPreview
@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getPagerMoviesUseCase: GetPagerMoviesUseCase,
) : ViewModel() {


    private val _responseBy: MutableLiveData<ResponseUser> = MutableLiveData<ResponseUser>()
    private val _queryBy: MutableLiveData<String> = MutableLiveData<String>()
    var oldResponse: ResponseUser = ResponseUser.POPULAR

    init {
        _queryBy.value = ""
    }

    val movieFlow: Flow<PagingData<Movie>> by lazy {
        _responseBy.asFlow()
            .flatMapLatest {
                getPagerMoviesUseCase.exesute(response = it, query = _queryBy.value!!)
            }
            .cachedIn(viewModelScope)
    }

    fun responseType(newResponse: ResponseUser) {
        oldResponse = newResponse
        _responseBy.value = newResponse
    }

    fun responseSearchType(newQuery: String) {
        _queryBy.value = newQuery
        _responseBy.value = ResponseUser.SEARCH
    }
}