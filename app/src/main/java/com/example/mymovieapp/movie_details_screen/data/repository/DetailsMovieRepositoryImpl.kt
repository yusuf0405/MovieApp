package com.example.mymovieapp.movie_details_screen.data.repository

import com.example.mymovieapp.app.api.RetrofitInstance
import com.example.mymovieapp.movie_details_screen.domain.models.MovieDetails
import com.example.mymovieapp.movie_details_screen.domain.models.TrailerResponse
import com.example.mymovieapp.movie_details_screen.domain.repository.DetailsMovieRepository
import com.example.mymovieapp.movie_screen.domain.model.MovieResponse
import retrofit2.Response

class DetailsMovieRepositoryImpl : DetailsMovieRepository {

    override suspend fun getMovieDetails(id: Int): Response<MovieDetails> =
        RetrofitInstance.movieApi.getMovieDetails(id = id)


    override suspend fun getMovieTrailer(id: Int): Response<TrailerResponse> =
        RetrofitInstance.movieApi.getMovieTrailer(id = id)


    override suspend fun getSimilarMovie(id: Int): Response<MovieResponse> =
        RetrofitInstance.movieApi.getSimilarMovie(id = id)

}