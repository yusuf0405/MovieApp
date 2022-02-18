package com.example.mymovieapp.movie_screen.domain.repository

import com.example.mymovieapp.movie_screen.domain.model.MovieResponse
import retrofit2.Response

interface MovieRepository {

    suspend fun getPopularMovie(page: Int, pageSize: Int): Response<MovieResponse>

    suspend fun getTopRatedMovie(page: Int, pageSize: Int): Response<MovieResponse>

    suspend fun getUpcomingMovie(page: Int, pageSize: Int): Response<MovieResponse>

    suspend fun getNowPlayingMovie(page: Int, pageSize: Int): Response<MovieResponse>

    suspend fun getSearchMovie(query: String, page: Int, pageSize: Int): Response<MovieResponse>
}