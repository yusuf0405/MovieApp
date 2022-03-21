package com.example.mymovieapp.app.room_db.person

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mymovieapp.screen_favorite.domain.models.FavoritePerson

@Database(entities = [FavoritePerson::class], version = 1)
abstract class FavoritePersonsDB : RoomDatabase() {
    abstract fun personsDao(): FavoritePersonDao
}
