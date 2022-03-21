package com.example.mymovieapp.screen_favorite.data.repository

import com.example.mymovieapp.app.room_db.movie.FavoriteMovieDao
import com.example.mymovieapp.screen_favorite.domain.models.FavoriteMovie
import com.example.mymovieapp.screen_favorite.domain.repository.FavoriteMovieRepository
import javax.inject.Inject

class FavoriteMovieRepositoryImpl @Inject constructor(private val movieDao: FavoriteMovieDao) :
    FavoriteMovieRepository {

    override suspend fun addNewMovie(movie: FavoriteMovie) = movieDao.addNewMovie(movie = movie)

    override suspend fun deleteMovie(movie: FavoriteMovie) = movieDao.deleteMovie(movie = movie)

    override suspend fun allFavoriteMovies() = movieDao.getAllMovie()

    override suspend fun getFavoriteMovie(id: Int): FavoriteMovie = movieDao.getMovie(movieId = id)
}