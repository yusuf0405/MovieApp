package com.example.mymovieapp.movie_screen.domain.repository

import androidx.paging.PagingData
import com.example.mymovieapp.movie_screen.domain.model.Movie
import com.example.mymovieapp.movie_screen.domain.model.MovieResponse
import com.example.mymovieapp.movie_screen.domain.model.ResponseUser
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface MovieRepository {

    suspend fun getPopularMovie(page: Int, pageSize: Int): Response<MovieResponse>

    suspend fun getTopRatedMovie(page: Int, pageSize: Int): Response<MovieResponse>

    suspend fun getUpcomingMovie(page: Int, pageSize: Int): Response<MovieResponse>

    suspend fun getNowPlayingMovie(page: Int, pageSize: Int): Response<MovieResponse>

    suspend fun getSearchMovie(query: String, page: Int, pageSize: Int): Response<MovieResponse>

    fun createPagerMovies(response: ResponseUser, query: String): Flow<PagingData<Movie>>
}