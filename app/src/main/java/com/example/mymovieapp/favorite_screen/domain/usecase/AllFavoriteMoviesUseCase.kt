package com.example.mymovieapp.favorite_screen.domain.usecase

import com.example.mymovieapp.favorite_screen.domain.models.FavoriteMovie
import com.example.mymovieapp.favorite_screen.domain.repository.FavoriteMovieRepository
import javax.inject.Inject

class AllFavoriteMoviesUseCase @Inject constructor(private val repository: FavoriteMovieRepository) {
    suspend fun execute(): List<FavoriteMovie> = repository.allFavoriteMovies()
}