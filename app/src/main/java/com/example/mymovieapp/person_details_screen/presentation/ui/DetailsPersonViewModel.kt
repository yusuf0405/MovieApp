package com.example.mymovieapp.person_details_screen.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovieapp.person_details_screen.domain.model.MovieCredits
import com.example.mymovieapp.person_details_screen.domain.model.PersonDetails
import com.example.mymovieapp.person_details_screen.domain.repository.DetailsPersonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DetailsPersonViewModel @Inject constructor(private val repository: DetailsPersonRepository) :
    ViewModel() {

    private var _personOfList: MutableLiveData<Response<PersonDetails>> = MutableLiveData()
    val personOfList: LiveData<Response<PersonDetails>> = _personOfList

    private var _movieListResponse: MutableLiveData<Response<MovieCredits>> =
        MutableLiveData()
    val movieListResponse: LiveData<Response<MovieCredits>> = _movieListResponse

    fun getPersonDetails(id: Int) {
        viewModelScope.launch {
            val response: Response<PersonDetails> = repository.getPersonDetails(id = id)
            _personOfList.value = response

        }
    }

    fun getPersonCreditMovies(id: Int) {
        viewModelScope.launch {
            val response: Response<MovieCredits> = repository.getPersonCreditMovies(id = id)
            _movieListResponse.value = response

        }
    }

}