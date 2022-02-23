package com.example.mymovieapp.favorite_screen.data.repository

import com.example.mymovieapp.favorite_screen.data.favor_movie_db.FavoriteMovieDao
import com.example.mymovieapp.favorite_screen.domain.models.FavoriteMovie
import com.example.mymovieapp.favorite_screen.domain.repository.FavoriteMovieRepository
import javax.inject.Inject

class FavoriteMovieRepositoryImpl @Inject constructor(private val movieDao: FavoriteMovieDao) :
    FavoriteMovieRepository {

    override suspend fun addNewMovie(movie: FavoriteMovie) = movieDao.addNewMovie(movie = movie)

    override suspend fun deleteMovie(movie: FavoriteMovie) = movieDao.deleteMovie(movie = movie)

    override suspend fun allFavoriteMovies() = movieDao.getAllMovie()
}