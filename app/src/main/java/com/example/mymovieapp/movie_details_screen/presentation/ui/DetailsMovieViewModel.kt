package com.example.mymovieapp.movie_details_screen.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
) : ViewModel() {

    private var _movieOfList: MutableLiveData<Response<MovieDetails>> = MutableLiveData()
    val movieOfList: LiveData<Response<MovieDetails>> = _movieOfList

    private var _movieListTrailerResponse: MutableLiveData<Response<TrailerResponse>> =
        MutableLiveData()
    val movieListTrailerResponse: LiveData<Response<TrailerResponse>> = _movieListTrailerResponse

    private var _movieSimilarResponse: MutableLiveData<Response<MovieResponse>> = MutableLiveData()
    val movieSimilarResponse: LiveData<Response<MovieResponse>> = _movieSimilarResponse


    fun getMovieDetails(id: Int) {
        viewModelScope.launch {
            _movieOfList.value = getMovieDetailsUseCase.exesute(id = id)
        }
    }

    fun getMovieTrailer(id: Int) {
        viewModelScope.launch {
            _movieListTrailerResponse.value = getMovieTrailerUseCase.exesute(id = id)
        }
    }

    fun getSimilarMovie(id: Int) {
        viewModelScope.launch {
            _movieSimilarResponse.value = getSimilarMovieUseCase.exesute(id = id)
        }
    }
}