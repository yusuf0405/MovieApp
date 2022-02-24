package com.example.mymovieapp.movie_screen.data.repository

import com.example.mymovieapp.app.api.RetrofitInstance
import com.example.mymovieapp.movie_screen.domain.model.MovieResponse
import com.example.mymovieapp.movie_screen.domain.repository.MovieRepository
import retrofit2.Response

class MovieRepositoryImpl : MovieRepository {
    override suspend fun getPopularMovie(page: Int, pageSize: Int): Response<MovieResponse> =
        RetrofitInstance.movieApi.getPopularMovie(page = page, pageSize = pageSize)


    override suspend fun getTopRatedMovie(page: Int, pageSize: Int): Response<MovieResponse> =
        RetrofitInstance.movieApi.getTopRatedMovie(page = page, pageSize = pageSize)


    override suspend fun getUpcomingMovie(page: Int, pageSize: Int): Response<MovieResponse> =
        RetrofitInstance.movieApi.getTopUpcomingMovie(page = page, pageSize = pageSize)


    override suspend fun getNowPlayingMovie(page: Int, pageSize: Int): Response<MovieResponse> =
        RetrofitInstance.movieApi.getNowPlayingMovie(page = page, pageSize = pageSize)


    override suspend fun getSearchMovie(
        query: String,
        page: Int,
        pageSize: Int,
    ): Response<MovieResponse> =
        RetrofitInstance.movieApi.getSearchMovie(query = query,
            page = page,
            pageSize = pageSize)


}