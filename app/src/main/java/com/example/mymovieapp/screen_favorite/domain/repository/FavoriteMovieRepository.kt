package com.example.mymovieapp.screen_favorite.domain.repository

import com.example.mymovieapp.screen_favorite.domain.models.FavoriteMovie

interface FavoriteMovieRepository {

    suspend fun addNewMovie(movie: FavoriteMovie)

    suspend fun deleteMovie(movie: FavoriteMovie)

    suspend fun allFavoriteMovies(): List<FavoriteMovie>

    suspend fun getFavoriteMovie(id: Int): FavoriteMovie
}