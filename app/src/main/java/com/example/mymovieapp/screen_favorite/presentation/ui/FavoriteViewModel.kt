package com.example.mymovieapp.screen_favorite.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovieapp.screen_favorite.domain.models.FavoriteMovie
import com.example.mymovieapp.screen_favorite.domain.models.FavoritePerson
import com.example.mymovieapp.screen_favorite.domain.usecase.AllFavoriteMoviesUseCase
import com.example.mymovieapp.screen_favorite.domain.usecase.AllFavoritePersonsUseCase
import com.example.mymovieapp.screen_favorite.domain.usecase.DeleteMovieFavoriteUseCase
import com.example.mymovieapp.screen_favorite.domain.usecase.DeletePersonFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@DelicateCoroutinesApi

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val allFavoriteMoviesUseCase: AllFavoriteMoviesUseCase,
    private val allFavoritePersonsUseCase: AllFavoritePersonsUseCase,
    private val deleteMovieFavoriteUseCase: DeleteMovieFavoriteUseCase,
    private val deletePersonFavoriteUseCase: DeletePersonFavoriteUseCase,
) : ViewModel() {
    private val _favoriteMovies: MutableLiveData<List<FavoriteMovie>> = MutableLiveData()
    val favoriteMovies: LiveData<List<FavoriteMovie>> = _favoriteMovies

    private val _favoritePersons: MutableLiveData<List<FavoritePerson>> = MutableLiveData()
    val favoritePersons: LiveData<List<FavoritePerson>> = _favoritePersons

    fun allFavoriteMovies() =
        viewModelScope.launch { _favoriteMovies.value = allFavoriteMoviesUseCase.execute() }

    fun allFavoritePersons() =
        viewModelScope.launch { _favoritePersons.value = allFavoritePersonsUseCase.execute() }


    fun deleteFavoriteMovies(movie: FavoriteMovie) {
        viewModelScope.launch {
            deleteMovieFavoriteUseCase.execute(movie = movie)
            _favoriteMovies.value = allFavoriteMoviesUseCase.execute()
        }
    }


    fun deleteFavoritePerson(person: FavoritePerson) {
        viewModelScope.launch {
            deletePersonFavoriteUseCase.execute(person = person)
            _favoritePersons.value = allFavoritePersonsUseCase.execute()
        }
    }
}