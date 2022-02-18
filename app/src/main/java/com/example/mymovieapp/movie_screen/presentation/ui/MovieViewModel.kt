package com.example.mymovieapp.movie_screen.presentation.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mymovieapp.movie_screen.domain.model.Movie
import com.example.mymovieapp.movie_screen.domain.model.ResponseUser
import com.example.mymovieapp.movie_screen.domain.repository.MovieRepository
import com.example.mymovieapp.movie_screen.data.souse.MoviePageSource
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
    private val repository: MovieRepository,
) : ViewModel() {


    private val _responseBy: MutableLiveData<ResponseUser> = MutableLiveData<ResponseUser>()
    private var query = ""
    var oldResponse: ResponseUser = ResponseUser.POPULAR

    val movieFlow: Flow<PagingData<Movie>> by lazy {
        _responseBy.asFlow()
            .flatMapLatest {
                getPagerMovies(it)
            }
            .cachedIn(viewModelScope)
    }

    private fun getPagerMovies(response: ResponseUser): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 2,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviePageSource(api = repository,
                    responseType = response,
                    query = query)
            }
        ).flow
    }

    fun responseType(newResponse: ResponseUser) {
        oldResponse = newResponse
        _responseBy.value = newResponse
    }

    fun responseSearchType(newQuery: String) {
        query = newQuery
        _responseBy.value = ResponseUser.SEARCH
    }
}