package com.example.mymovieapp.screen_favorite.domain.usecase

import com.example.mymovieapp.screen_favorite.domain.models.FavoriteMovie
import com.example.mymovieapp.screen_favorite.domain.repository.FavoriteMovieRepository
import javax.inject.Inject

class DeleteMovieFavoriteUseCase @Inject constructor(private val repository: FavoriteMovieRepository) {
    suspend fun execute(movie: FavoriteMovie) {
        repository.deleteMovie(movie = movie)
    }
}