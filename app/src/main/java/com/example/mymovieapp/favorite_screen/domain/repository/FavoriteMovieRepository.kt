package com.example.mymovieapp.favorite_screen.domain.repository

import com.example.mymovieapp.favorite_screen.domain.models.FavoriteMovie

interface FavoriteMovieRepository {

    suspend fun addNewMovie(movie: FavoriteMovie)

    suspend fun deleteMovie(movie: FavoriteMovie)

    suspend fun allFavoriteMovies(): List<FavoriteMovie>
}