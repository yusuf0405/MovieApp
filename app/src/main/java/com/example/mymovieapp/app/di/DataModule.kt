package com.example.mymovieapp.app.di

import com.example.mymovieapp.app.api.MovieApi
import com.example.mymovieapp.app.api.PersonApi
import com.example.mymovieapp.favorite_screen.data.favor_movie_db.FavoriteMovieDao
import com.example.mymovieapp.favorite_screen.data.favor_person_db.FavoritePersonDao
import com.example.mymovieapp.favorite_screen.data.repository.FavoriteMovieRepositoryImpl
import com.example.mymovieapp.favorite_screen.data.repository.FavoritePersonRepositoryImpl
import com.example.mymovieapp.favorite_screen.domain.repository.FavoriteMovieRepository
import com.example.mymovieapp.favorite_screen.domain.repository.FavoritePersonRepository
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