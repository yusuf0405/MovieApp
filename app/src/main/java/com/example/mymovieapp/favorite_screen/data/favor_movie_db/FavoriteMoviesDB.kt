package com.example.mymovieapp.favorite_screen.data.favor_movie_db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mymovieapp.favorite_screen.data.favor_movie_db.FavoriteMovieDao
import com.example.mymovieapp.favorite_screen.domain.model.FavoriteMovie


@Database(entities = [FavoriteMovie::class], version = 1)
abstract class FavoriteMoviesDB : RoomDatabase() {
    abstract fun moviesDao(): FavoriteMovieDao
}