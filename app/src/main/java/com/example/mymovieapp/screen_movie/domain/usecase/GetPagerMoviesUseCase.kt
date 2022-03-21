package com.example.mymovieapp.screen_movie.domain.usecase

import androidx.paging.PagingData
import com.example.mymovieapp.screen_movie.domain.model.Movie
import com.example.mymovieapp.screen_movie.domain.model.ResponseUser
import com.example.mymovieapp.screen_movie.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPagerMoviesUseCase @Inject constructor(private val repository: MovieRepository) {
    fun exesute(response: ResponseUser, query: String): Flow<PagingData<Movie>> =
        repository.createPagerMovies(response = response, query = query)
}