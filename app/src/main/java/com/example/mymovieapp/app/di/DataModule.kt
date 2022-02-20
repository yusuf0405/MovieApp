package com.example.mymovieapp.app.di

import android.content.Context
import androidx.room.Room
import com.example.mymovieapp.favorite_screen.data.favor_movie_db.FavoriteMoviesDB
import com.example.mymovieapp.favorite_screen.data.favor_person_db.FavoritePersonsDB
import com.example.mymovieapp.movie_details_screen.data.repository.DetailsMovieRepositoryImpl
import com.example.mymovieapp.movie_details_screen.domain.repository.DetailsMovieRepository
import com.example.mymovieapp.movie_screen.data.repository.MovieRepositoryImpl
import com.example.mymovieapp.movie_screen.domain.repository.MovieRepository
import com.example.mymovieapp.person_details_screen.data.repository.DetailsPersonRepositoryImpl
import com.example.mymovieapp.person_details_screen.domain.repository.DetailsPersonRepository
import com.example.mymovieapp.person_screen.data.repository.PersonRepositoryImpl
import com.example.mymovieapp.person_screen.domain.repository.PersonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    // <!--  Movie Repository create   -->
    @Provides
    @Singleton
    fun provideMovieRepository(): MovieRepository = MovieRepositoryImpl()

    // <!--  Person Repository create   -->
    @Provides
    @Singleton
    fun providePersonRepository(): PersonRepository = PersonRepositoryImpl()

    // <!-- Movie Details Repository create   -->
    @Provides
    @Singleton
    fun provideDetailsMovieRepository(): DetailsMovieRepository = DetailsMovieRepositoryImpl()

    // <!-- Person Details Repository create   -->
    @Provides
    @Singleton
    fun provideDetailsPersonRepository(): DetailsPersonRepository = DetailsPersonRepositoryImpl()

    @Singleton // Tell Dagger-Hilt to create a singleton accessible everywhere in ApplicationComponent (i.e. everywhere in the application)
    @Provides
    fun provideFavoriteMoviesDB(
        @ApplicationContext app: Context,
    ) = Room.databaseBuilder(
        app,
        FavoriteMoviesDB::class.java,
        "favorite_movies"
    ).build() // The reason we can construct a database for the repo

    @Singleton
    @Provides
    fun provideMoviesDao(db: FavoriteMoviesDB) =
        db.moviesDao() // The reason we can implement a Dao for the database

    @Singleton // Tell Dagger-Hilt to create a singleton accessible everywhere in ApplicationComponent (i.e. everywhere in the application)
    @Provides
    fun provideFavoritePersonsDB(
        @ApplicationContext app: Context,
    ) = Room.databaseBuilder(
        app,
        FavoritePersonsDB::class.java,
        "favorite_persons"
    ).build() // The reason we can construct a database for the repo

    @Singleton
    @Provides
    fun providePersonsDao(db: FavoritePersonsDB) =
        db.personsDao()
// The reason we can implement a Dao for the database

}