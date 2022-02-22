package com.example.mymovieapp.app.api

import androidx.annotation.IntRange
import com.example.mymovieapp.app.utils.Utils.Companion.DEFAULT_PAGE_SIZE
import com.example.mymovieapp.app.utils.Utils.Companion.MAX_PAGE_SIZE
import com.example.mymovieapp.movie_screen.domain.model.MovieResponse
import com.example.mymovieapp.movie_details_screen.domain.model.MovieDetails
import com.example.mymovieapp.movie_details_screen.domain.model.TrailerResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieApi {

    @GET("movie/popular")
    suspend fun getPopularMovie(
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("pageSize") @IntRange(from = 1,
            to = MAX_PAGE_SIZE.toLong()) pageSize: Int = DEFAULT_PAGE_SIZE,
    ): Response<MovieResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovie(
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("pageSize") @IntRange(from = 1,
            to = MAX_PAGE_SIZE.toLong()) pageSize: Int = DEFAULT_PAGE_SIZE,
    ): Response<MovieResponse>

    @GET("movie/upcoming")
    suspend fun getTopUpcomingMovie(
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("pageSize") @IntRange(from = 1,
            to = MAX_PAGE_SIZE.toLong()) pageSize: Int = DEFAULT_PAGE_SIZE,
    ): Response<MovieResponse>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovie(
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("pageSize") @IntRange(from = 1,
            to = MAX_PAGE_SIZE.toLong()) pageSize: Int = DEFAULT_PAGE_SIZE,
    ): Response<MovieResponse>


    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") id: Int): Response<MovieDetails>

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieTrailer(@Path("movie_id") id: Int): Response<TrailerResponse>


    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovie(@Path("movie_id") id: Int): Response<MovieResponse>

    @GET("search/movie")
    suspend fun getSearchMovie(
        @Query("query") query: String,
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("pageSize") @IntRange(from = 1,
            to = MAX_PAGE_SIZE.toLong()) pageSize: Int = DEFAULT_PAGE_SIZE,
    ): Response<MovieResponse>

}