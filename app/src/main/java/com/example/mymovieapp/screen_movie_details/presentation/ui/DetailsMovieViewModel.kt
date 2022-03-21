package com.example.mymovieapp.screen_movie_details.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovieapp.app.utils.Resource
import com.example.mymovieapp.app.utils.toFavoriteMovies
import com.example.mymovieapp.screen_favorite.domain.usecase.AddMovieFavoriteUseCase
import com.example.mymovieapp.screen_favorite.domain.usecase.DeleteMovieFavoriteUseCase
import com.example.mymovieapp.screen_favorite.domain.usecase.GetFavoriteMovieUseCase
import com.example.mymovieapp.screen_movie_details.domain.models.MovieDetails
import com.example.mymovieapp.screen_movie_details.domain.models.Trailer
import com.example.mymovieapp.screen_movie_details.domain.usecase.GetMovieDetailsUseCase
import com.example.mymovieapp.screen_movie_details.domain.usecase.GetMovieTrailerUseCase
import com.example.mymovieapp.screen_movie_details.domain.usecase.GetSimilarMovieUseCase
import com.example.mymovieapp.screen_movie.domain.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class MovieDetailsResource(
    val trailerList: List<Trailer>,
    val movie: MovieDetails,
    val movieSimilar: List<Movie>,
)

@HiltViewModel
class DetailsMovieViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieTrailerUseCase: GetMovieTrailerUseCase,
    private val getSimilarMovieUseCase: GetSimilarMovieUseCase,
    private val addMovieFavoriteUseCase: AddMovieFavoriteUseCase,
    private val deleteMovieFavoriteUseCase: DeleteMovieFavoriteUseCase,
    private val getFavoriteMovieUseCase: GetFavoriteMovieUseCase,
) : ViewModel() {

    private val _movieId: MutableLiveData<Int> = MutableLiveData()

    private val _resource: MutableLiveData<Resource<MovieDetailsResource>> = MutableLiveData()
    val resource: LiveData<Resource<MovieDetailsResource>> = _resource

    private val _isFavorite: MutableLiveData<Boolean> = MutableLiveData()
    val isFavorite: LiveData<Boolean> = _isFavorite

    fun setMovieId(id: Int) {
        _movieId.value = id
        chekIsFavorite()
    }

    private fun chekIsFavorite() = viewModelScope.launch {
        val movie = getFavoriteMovieUseCase.execute(id = _movieId.value!!)
        _isFavorite.value = movie != null
        getMovieDetails()
    }

    private fun getMovieDetails() = viewModelScope.launch {
        _resource.value = Resource.loading(data = null)
        val id = _movieId.value!!
        try {
            val movieRes = withContext(Dispatchers.IO) { getMovieDetailsUseCase.exesute(id = id) }
            val trailerRes = withContext(Dispatchers.IO) { getMovieTrailerUseCase.exesute(id = id) }
            val similarRes = withContext(Dispatchers.IO) { getSimilarMovieUseCase.exesute(id = id) }

            if (movieRes.isSuccessful && trailerRes.isSuccessful && similarRes.isSuccessful) {
                val allResource = MovieDetailsResource(
                    trailerList = trailerRes.body()!!.trailerList,
                    movie = movieRes.body()!!,
                    movieSimilar = similarRes.body()!!.movieList
                )
                _resource.value = Resource.success(data = allResource)
            } else _resource.value = Resource.error(data = null, message = movieRes.message())
        } catch (e: Exception) {
            _resource.value = Resource.error(data = null, message = e.message)
        }
    }


    fun addMovieFavorite(movie: MovieDetails) =
        viewModelScope.launch { addMovieFavoriteUseCase.execute(movie = movie.toFavoriteMovies()) }


    fun deleteMovieFavorite(movie: MovieDetails) =
        viewModelScope.launch { deleteMovieFavoriteUseCase.execute(movie = movie.toFavoriteMovies()) }

}
