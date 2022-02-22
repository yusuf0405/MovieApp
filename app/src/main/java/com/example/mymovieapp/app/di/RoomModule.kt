package com.example.mymovieapp.app.di

import android.content.Context
import androidx.room.Room
import com.example.mymovieapp.favorite_screen.data.favor_movie_db.FavoriteMoviesDB
import com.example.mymovieapp.favorite_screen.data.favor_person_db.FavoritePersonsDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {
    // <!-- Favorite Movie Room DB and Dao class create   -->
    @Provides
    @Singleton
    fun provideFavoriteMoviesDB(
        @ApplicationContext app: Context,
    ) = Room.databaseBuilder(
        app,
        FavoriteMoviesDB::class.java,
        "favorite_movies"
    ).build()

    @Provides
    @Singleton
    fun provideMoviesDao(db: FavoriteMoviesDB) =
        db.moviesDao()

    // <!-- Favorite Person Room DB and Dao class create   -->
    @Provides
    @Singleton
    fun provideFavoritePersonsDB(
        @ApplicationContext app: Context,
    ) = Room.databaseBuilder(
        app,
        FavoritePersonsDB::class.java,
        "favorite_persons"
    ).build()

    @Provides
    @Singleton
    fun providePersonsDao(db: FavoritePersonsDB) =
        db.personsDao()

}