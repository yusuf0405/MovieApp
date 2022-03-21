package com.example.mymovieapp.screen_movie_details.data

import com.example.mymovieapp.app.api.MovieApi
import com.example.mymovieapp.screen_movie_details.domain.models.MovieDetails
import com.example.mymovieapp.screen_movie_details.domain.models.TrailerResponse
import com.example.mymovieapp.screen_movie_details.domain.repository.DetailsMovieRepository
import com.example.mymovieapp.screen_movie.domain.model.MovieResponse
import retrofit2.Response
import javax.inject.Inject

class DetailsMovieRepositoryImpl @Inject constructor(private val movieApi: MovieApi) : DetailsMovieRepository {

    override suspend fun getMovieDetails(id: Int): Response<MovieDetails> =
        movieApi.getMovieDetails(id = id)

    override suspend fun getMovieTrailer(id: Int): Response<TrailerResponse> =
        movieApi.getMovieTrailer(id = id)

    override suspend fun getSimilarMovie(id: Int): Response<MovieResponse> =
        movieApi.getSimilarMovie(id = id)

}