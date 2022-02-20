package com.example.mymovieapp.favorite_screen.domain.usecase

import com.example.mymovieapp.favorite_screen.data.repository.FavoriteMovieRepository
import com.example.mymovieapp.favorite_screen.domain.model.FavoriteMovie
import javax.inject.Inject

class DeleteMovieFavoriteUseCase @Inject constructor(private val repository: FavoriteMovieRepository) {
    suspend fun execute(movie: FavoriteMovie) {
        repository.deleteMovie(movie = movie)
    }
}