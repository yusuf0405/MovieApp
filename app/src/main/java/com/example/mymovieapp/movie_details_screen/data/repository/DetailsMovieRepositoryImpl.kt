package com.example.mymovieapp.movie_details_screen.data.repository

import com.example.mymovieapp.movie_screen.domain.model.MovieResponse
import com.example.mymovieapp.movie_details_screen.domain.model.MovieDetails
import com.example.mymovieapp.movie_details_screen.domain.model.TrailerResponse
import com.example.mymovieapp.movie_details_screen.domain.repository.DetailsMovieRepository
import com.example.mymovieapp.app.network.RetrofitInstance
import retrofit2.Response

class DetailsMovieRepositoryImpl : DetailsMovieRepository {
    override suspend fun getMovieDetails(id: Int): Response<MovieDetails> {
        return RetrofitInstance.movieApi.getMovieDetails(id = id)
    }

    override suspend fun getMovieTrailer(id: Int): Response<TrailerResponse> {
        return RetrofitInstance.movieApi.getMovieTrailer(id = id)
    }

    override suspend fun getSimilarMovie(id: Int): Response<MovieResponse> {
        return RetrofitInstance.movieApi.getSimilarMovie(id = id)
    }
}