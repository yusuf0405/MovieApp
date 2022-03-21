package com.example.mymovieapp.app.room_db.movie

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mymovieapp.screen_favorite.domain.models.FavoriteMovie


@Database(entities = [FavoriteMovie::class], version = 1)
abstract class FavoriteMoviesDB : RoomDatabase() {
    abstract fun moviesDao(): FavoriteMovieDao
}