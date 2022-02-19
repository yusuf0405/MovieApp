package com.example.mymovieapp.app.di

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
    @Provides
    @Singleton
    fun provideMovieRepository(): MovieRepository = MovieRepositoryImpl()

    @Provides
    @Singleton
    fun providePersonRepository(): PersonRepository = PersonRepositoryImpl()

    @Provides
    @Singleton
    fun provideDetailsMovieRepository(): DetailsMovieRepository = DetailsMovieRepositoryImpl()

    @Provides
    @Singleton
    fun provideDetailsPersonRepository(): DetailsPersonRepository = DetailsPersonRepositoryImpl()
}