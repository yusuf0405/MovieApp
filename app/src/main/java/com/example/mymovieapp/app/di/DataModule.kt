package com.example.mymovieapp.app.di

import com.example.mymovieapp.app.api.MovieApi
import com.example.mymovieapp.app.api.PersonApi
import com.example.mymovieapp.app.room_db.movie.FavoriteMovieDao
import com.example.mymovieapp.app.room_db.person.FavoritePersonDao
import com.example.mymovieapp.screen_favorite.data.repository.FavoriteMovieRepositoryImpl
import com.example.mymovieapp.screen_favorite.data.repository.FavoritePersonRepositoryImpl
import com.example.mymovieapp.screen_favorite.domain.repository.FavoriteMovieRepository
import com.example.mymovieapp.screen_favorite.domain.repository.FavoritePersonRepository
import com.example.mymovieapp.screen_movie_details.data.DetailsMovieRepositoryImpl
import com.example.mymovieapp.screen_movie_details.domain.repository.DetailsMovieRepository
import com.example.mymovieapp.screen_movie.data.repository.MovieRepositoryImpl
import com.example.mymovieapp.screen_movie.domain.repository.MovieRepository
import com.example.mymovieapp.screen_person_details.data.DetailsPersonRepositoryImpl
import com.example.mymovieapp.screen_person_details.domain.repository.DetailsPersonRepository
import com.example.mymovieapp.screen_person.data.repository.PersonRepositoryImpl
import com.example.mymovieapp.screen_person.domain.repository.PersonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    // <!--  Movie Repository create   -->
    @Provides
    @Singleton
    fun provideMovieRepository(movieApi: MovieApi): MovieRepository =
        MovieRepositoryImpl(movieApi = movieApi)

    // <!--  Person Repository create   -->
    @Provides
    @Singleton
    fun providePersonRepository(personApi: PersonApi): PersonRepository =
        PersonRepositoryImpl(personApi = personApi)

    // <!-- Movie Details Repository create   -->
    @Provides
    @Singleton
    fun provideDetailsMovieRepository(movieApi: MovieApi): DetailsMovieRepository =
        DetailsMovieRepositoryImpl(movieApi = movieApi)

    // <!-- Person Details Repository create   -->
    @Provides
    @Singleton
    fun provideDetailsPersonRepository(personApi: PersonApi): DetailsPersonRepository =
        DetailsPersonRepositoryImpl(personApi = personApi)

    // <!-- Favorite Person Repository create   -->
    @Provides
    @Singleton
    fun provideFavPersonRepository(dao: FavoritePersonDao): FavoritePersonRepository =
        FavoritePersonRepositoryImpl(dao)

    // <!-- Favorite Movie Repository create   -->
    @Provides
    @Singleton
    fun provideFavMovieRepository(dao: FavoriteMovieDao): FavoriteMovieRepository =
        FavoriteMovieRepositoryImpl(dao)


}