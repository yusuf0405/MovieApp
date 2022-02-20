package com.example.mymovieapp.app.di

import com.example.mymovieapp.movie_details_screen.domain.repository.DetailsMovieRepository
import com.example.mymovieapp.movie_details_screen.domain.usecase.GetMovieDetailsUseCase
import com.example.mymovieapp.movie_details_screen.domain.usecase.GetMovieTrailerUseCase
import com.example.mymovieapp.movie_details_screen.domain.usecase.GetSimilarMovieUseCase
import com.example.mymovieapp.movie_screen.domain.repository.MovieRepository
import com.example.mymovieapp.movie_screen.domain.usecase.GetPagerMoviesUseCase
import com.example.mymovieapp.person_details_screen.domain.repository.DetailsPersonRepository
import com.example.mymovieapp.person_details_screen.domain.usecase.GetPersonCreditMoviesUseCase
import com.example.mymovieapp.person_details_screen.domain.usecase.GetPersonDetailsUseCase
import com.example.mymovieapp.person_screen.domain.repository.PersonRepository
import com.example.mymovieapp.person_screen.domain.usecase.GetPagerPersonUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {
    // <!--  Movie Details Screen UseCases   -->
    @Provides
    fun provideGetMovieDetailsUseCase(repository: DetailsMovieRepository): GetMovieDetailsUseCase =
        GetMovieDetailsUseCase(repository = repository)

    @Provides
    fun provideGetMovieTrailerUseCase(repository: DetailsMovieRepository): GetMovieTrailerUseCase =
        GetMovieTrailerUseCase(repository = repository)

    @Provides
    fun provideGetSimilarMovieUseCase(repository: DetailsMovieRepository): GetSimilarMovieUseCase =
        GetSimilarMovieUseCase(repository = repository)


    // <!--  Person Details Screen UseCases   -->
    @Provides
    fun     provideGetPersonDetailsUseCase(repository: DetailsPersonRepository): GetPersonDetailsUseCase =
        GetPersonDetailsUseCase(repository = repository)

    @Provides
    fun provideGetPersonCreditMoviesUseCase(repository: DetailsPersonRepository): GetPersonCreditMoviesUseCase =
        GetPersonCreditMoviesUseCase(repository = repository)


    // <!--  Person  Screen UseCase   -->
    @Provides
    fun provideGetPagerPersonUseCase(repository: PersonRepository): GetPagerPersonUseCase =
        GetPagerPersonUseCase(repository = repository)

    // <!--  Movie  Screen UseCase   -->
    @Provides
    fun provideGetPagerMoviesUseCase(repository: MovieRepository): GetPagerMoviesUseCase =
        GetPagerMoviesUseCase(repository = repository)


}