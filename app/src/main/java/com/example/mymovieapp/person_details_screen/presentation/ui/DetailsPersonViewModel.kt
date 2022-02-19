package com.example.mymovieapp.person_details_screen.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovieapp.person_details_screen.domain.model.MovieCredits
import com.example.mymovieapp.person_details_screen.domain.model.PersonDetails
import com.example.mymovieapp.person_details_screen.domain.usecase.GetPersonCreditMoviesUseCase
import com.example.mymovieapp.person_details_screen.domain.usecase.GetPersonDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DetailsPersonViewModel @Inject constructor(
    private val getPersonDetailsUseCase: GetPersonDetailsUseCase,
    private val getPersonCreditMoviesUseCase: GetPersonCreditMoviesUseCase,
) :
    ViewModel() {

    private var _personOfList: MutableLiveData<Response<PersonDetails>> = MutableLiveData()
    val personOfList: LiveData<Response<PersonDetails>> = _personOfList

    private var _movieListResponse: MutableLiveData<Response<MovieCredits>> = MutableLiveData()
    val movieListResponse: LiveData<Response<MovieCredits>> = _movieListResponse

    fun getPersonDetails(id: Int) {
        viewModelScope.launch {
            _personOfList.value = getPersonDetailsUseCase.exesute(id = id)

        }
    }

    fun getPersonCreditMovies(id: Int) {
        viewModelScope.launch {
            _movieListResponse.value = getPersonCreditMoviesUseCase.exesute(id = id)

        }
    }

}