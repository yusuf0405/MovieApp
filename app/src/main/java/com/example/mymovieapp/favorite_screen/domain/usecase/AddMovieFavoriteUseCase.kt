package com.example.mymovieapp.favorite_screen.domain.usecase

import com.example.mymovieapp.favorite_screen.data.repository.FavoriteMovieRepository
import com.example.mymovieapp.favorite_screen.domain.model.FavoriteMovie
import javax.inject.Inject

class AddMovieFavoriteUseCase @Inject constructor(private val repository: FavoriteMovieRepository) {
    suspend fun execute(movie: FavoriteMovie) {
        repository.addNewMovie(movie = movie)

    }
}