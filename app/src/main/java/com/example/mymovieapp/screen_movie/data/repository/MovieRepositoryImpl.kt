package com.example.mymovieapp.screen_movie.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mymovieapp.app.api.MovieApi
import com.example.mymovieapp.screen_movie.data.source.MoviePageSource
import com.example.mymovieapp.screen_movie.domain.model.Movie
import com.example.mymovieapp.screen_movie.domain.model.MovieResponse
import com.example.mymovieapp.screen_movie.domain.model.ResponseUser
import com.example.mymovieapp.screen_movie.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val movieApi: MovieApi) : MovieRepository {

    override suspend fun getPopularMovie(page: Int, pageSize: Int): Response<MovieResponse> =
        movieApi.getPopularMovie(page = page, pageSize = pageSize)


    override suspend fun getTopRatedMovie(page: Int, pageSize: Int): Response<MovieResponse> =
        movieApi.getTopRatedMovie(page = page, pageSize = pageSize)


    override suspend fun getUpcomingMovie(page: Int, pageSize: Int): Response<MovieResponse> =
        movieApi.getTopUpcomingMovie(page = page, pageSize = pageSize)


    override suspend fun getNowPlayingMovie(page: Int, pageSize: Int): Response<MovieResponse> =
        movieApi.getNowPlayingMovie(page = page, pageSize = pageSize)


    override suspend fun getSearchMovie(
        query: String,
        page: Int,
        pageSize: Int,
    ): Response<MovieResponse> =
        movieApi.getSearchMovie(query = query,
            page = page,
            pageSize = pageSize)

    override fun createPagerMovies(response: ResponseUser, query: String): Flow<PagingData<Movie>> =
        Pager(
            config = PagingConfig(
                pageSize = 2,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviePageSource(api = this,
                    responseType = response,
                    query = query)
            }
        ).flow

}