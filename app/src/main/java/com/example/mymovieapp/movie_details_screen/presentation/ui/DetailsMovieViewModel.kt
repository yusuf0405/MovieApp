package com.example.mymovieapp.movie_details_screen.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovieapp.app.toFavoriteMovies
import com.example.mymovieapp.favorite_screen.domain.model.FavoriteMovie
import com.example.mymovieapp.favorite_screen.domain.usecase.AddMovieFavoriteUseCase
import com.example.mymovieapp.favorite_screen.domain.usecase.AllFavoriteMoviesUseCase
import com.example.mymovieapp.favorite_screen.domain.usecase.DeleteMovieFavoriteUseCase
import com.example.mymovieapp.movie_details_screen.domain.model.MovieDetails
import com.example.mymovieapp.movie_details_screen.domain.model.TrailerResponse
import com.example.mymovieapp.movie_details_screen.domain.usecase.GetMovieDetailsUseCase
import com.example.mymovieapp.movie_details_screen.domain.usecase.GetMovieTrailerUseCase
import com.example.mymovieapp.movie_details_screen.domain.usecase.GetSimilarMovieUseCase
import com.example.mymovieapp.movie_screen.domain.model.MovieResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DetailsMovieViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieTrailerUseCase: GetMovieTrailerUseCase,
    private val getSimilarMovieUseCase: GetSimilarMovieUseCase,
    private val addMovieFavoriteUseCase: AddMovieFavoriteUseCase,
    private val deleteMovieFavoriteUseCase: DeleteMovieFavoriteUseCase,
    private val allFavoriteMoviesUseCase: AllFavoriteMoviesUseCase,
) : ViewModel() {

    private val _movieOfList: MutableLiveData<Response<MovieDetails>> = MutableLiveData()
    val movieOfList: LiveData<Response<MovieDetails>> = _movieOfList

    private val _trailerResponse: MutableLiveData<Response<TrailerResponse>> = MutableLiveData()
    val trailerResponse: LiveData<Response<TrailerResponse>> = _trailerResponse

    private val _movieSimilarResponse: MutableLiveData<Response<MovieResponse>> = MutableLiveData()
    val movieSimilarResponse: LiveData<Response<MovieResponse>> = _movieSimilarResponse


    fun getMovieDetails(id: Int) {
        viewModelScope.launch {
            _movieOfList.value = getMovieDetailsUseCase.exesute(id = id)
        }
    }

    fun getMovieTrailer(id: Int) {
        viewModelScope.launch {
            _trailerResponse.value = getMovieTrailerUseCase.exesute(id = id)
        }
    }

    fun getSimilarMovie(id: Int) {
        viewModelScope.launch {
            _movieSimilarResponse.value = getSimilarMovieUseCase.exesute(id = id)
        }
    }

    fun addMovieFavorite(movie: MovieDetails) {
        val favoriteMovie = movie.toFavoriteMovies()
        viewModelScope.launch {
            addMovieFavoriteUseCase.execute(movie = favoriteMovie)
        }
    }

    fun deleteMovieFavorite(movie: MovieDetails) {
        val favoriteMovie = movie.toFavoriteMovies()
        viewModelScope.launch {
            deleteMovieFavoriteUseCase.execute(movie = favoriteMovie)
        }
    }

    suspend fun allFavoriteMovies(): List<FavoriteMovie> {
        return allFavoriteMoviesUseCase.execute()


    }
}