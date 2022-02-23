package com.example.mymovieapp.favorite_screen.data.favor_person_db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mymovieapp.favorite_screen.domain.models.FavoritePerson

@Database(entities = [FavoritePerson::class], version = 1)
abstract class FavoritePersonsDB : RoomDatabase() {
    abstract fun personsDao(): FavoritePersonDao
}
