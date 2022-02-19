package com.example.mymovieapp.movie_screen.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mymovieapp.movie_screen.data.souse.MoviePageSource
import com.example.mymovieapp.movie_screen.domain.model.Movie
import com.example.mymovieapp.movie_screen.domain.model.ResponseUser
import com.example.mymovieapp.movie_screen.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPagerMoviesUseCase @Inject constructor(private val repository: MovieRepository) {
    fun exesute(response: ResponseUser, query: String): Flow<PagingData<Movie>> {
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
}