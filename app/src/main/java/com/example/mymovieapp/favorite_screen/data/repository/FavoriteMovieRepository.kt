package com.example.mymovieapp.favorite_screen.data.repository

import com.example.mymovieapp.favorite_screen.data.favor_movie_db.FavoriteMovieDao
import com.example.mymovieapp.favorite_screen.domain.model.FavoriteMovie
import javax.inject.Inject

class FavoriteMovieRepository @Inject constructor(private val movieDao: FavoriteMovieDao){

     suspend fun addNewMovie(movie: FavoriteMovie) = movieDao.addNewMovie(movie = movie)

     suspend fun deleteMovie(movie: FavoriteMovie) = movieDao.deleteMovie(movie = movie)

     suspend fun allFavoriteMovies() = movieDao.getAllMovie()
}