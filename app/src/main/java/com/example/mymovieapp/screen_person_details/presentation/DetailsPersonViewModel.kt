package com.example.mymovieapp.screen_person_details.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovieapp.app.utils.Resource
import com.example.mymovieapp.app.utils.toFavoritePerson
import com.example.mymovieapp.screen_favorite.domain.usecase.AddPersonFavoriteUseCase
import com.example.mymovieapp.screen_favorite.domain.usecase.DeletePersonFavoriteUseCase
import com.example.mymovieapp.screen_favorite.domain.usecase.GetFavoritePersonUseCase
import com.example.mymovieapp.screen_movie.domain.model.Movie
import com.example.mymovieapp.screen_person_details.domain.models.PersonDetails
import com.example.mymovieapp.screen_person_details.domain.usecase.GetPersonCreditMoviesUseCase
import com.example.mymovieapp.screen_person_details.domain.usecase.GetPersonDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class PersonDetailsResource(
    val person: PersonDetails,
    val creditMovies: List<Movie>,
)

@HiltViewModel
class DetailsPersonViewModel @Inject constructor(
    private val getFavoritePersonUseCase: GetFavoritePersonUseCase,
    private val getPersonDetailsUseCase: GetPersonDetailsUseCase,
    private val getCreditMoviesUseCase: GetPersonCreditMoviesUseCase,
    private val addPersonFavoriteUseCase: AddPersonFavoriteUseCase,
    private val deletePersonFavoriteUseCase: DeletePersonFavoriteUseCase,
) : ViewModel() {

    private val _personId: MutableLiveData<Int> = MutableLiveData()
    private val _isFavorite: MutableLiveData<Boolean> = MutableLiveData()
    val isFavorite: LiveData<Boolean> = _isFavorite

    private val _resource: MutableLiveData<Resource<PersonDetailsResource>> = MutableLiveData()
    val resource: LiveData<Resource<PersonDetailsResource>> = _resource


    fun setId(id: Int) {
        _personId.value = id
        chekIsFavorite()
    }

    private fun chekIsFavorite() = viewModelScope.launch {
        val person = getFavoritePersonUseCase.execute(id = _personId.value!!)
        _isFavorite.value = person != null
        getPersonDetails()
    }

    private fun getPersonDetails() = viewModelScope.launch {
        _resource.value = Resource.loading(data = null)
        val id = _personId.value!!
        try {
            val personRes = withContext(Dispatchers.IO) { getPersonDetailsUseCase.exesute(id = id) }
            val movieRes = withContext(Dispatchers.IO) { getCreditMoviesUseCase.exesute(id = id) }

            if (personRes.isSuccessful && movieRes.isSuccessful) {
                val allResource = PersonDetailsResource(
                    person = personRes.body()!!,
                    creditMovies = movieRes.body()!!.crew
                )
                _resource.value = Resource.success(data = allResource)
            } else _resource.value = Resource.error(data = null, message = movieRes.message())

        } catch (e: Exception) {
            _resource.value = Resource.error(data = null, message = e.message)
        }
    }


    fun addPersonFavorite(person: PersonDetails) =
        viewModelScope.launch { addPersonFavoriteUseCase.execute(person = person.toFavoritePerson()) }


    fun deletePersonFavorite(person: PersonDetails) =
        viewModelScope.launch { deletePersonFavoriteUseCase.execute(person = person.toFavoritePerson()) }


}