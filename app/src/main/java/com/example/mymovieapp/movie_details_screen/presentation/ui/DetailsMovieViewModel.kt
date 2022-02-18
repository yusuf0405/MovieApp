package com.example.mymovieapp.movie_details_screen.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovieapp.movie_screen.domain.model.MovieResponse
import com.example.mymovieapp.movie_details_screen.domain.model.MovieDetails
import com.example.mymovieapp.movie_details_screen.domain.model.TrailerResponse
import com.example.mymovieapp.movie_details_screen.domain.repository.DetailsMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DetailsMovieViewModel @Inject constructor(private val repository: DetailsMovieRepository) :
    ViewModel() {

    private var _movieOfList: MutableLiveData<Response<MovieDetails>> = MutableLiveData()
    val movieOfList: LiveData<Response<MovieDetails>> = _movieOfList

    private var _movieListTrailerResponse: MutableLiveData<Response<TrailerResponse>> =
        MutableLiveData()
    val movieListTrailerResponse: LiveData<Response<TrailerResponse>> = _movieListTrailerResponse

    private var _movieSimilarResponse: MutableLiveData<Response<MovieResponse>> = MutableLiveData()
    val movieSimilarResponse: LiveData<Response<MovieResponse>> = _movieSimilarResponse


    fun getMovieDetails(id: Int) {
        viewModelScope.launch {
            val response: Response<MovieDetails> = repository.getMovieDetails(id = id)
            _movieOfList.value = response
        }
    }

    fun getMovieTrailer(id: Int) {
        viewModelScope.launch {
            val response: Response<TrailerResponse> = repository.getMovieTrailer(id = id)
            _movieListTrailerResponse.value = response
        }
    }

    fun getSimilarMovie(id: Int) {
        viewModelScope.launch {
            val response: Response<MovieResponse> = repository.getSimilarMovie(id = id)
            _movieSimilarResponse.value = response
        }
    }
}