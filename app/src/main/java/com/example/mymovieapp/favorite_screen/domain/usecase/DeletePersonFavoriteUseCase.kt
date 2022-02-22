package com.example.mymovieapp.favorite_screen.domain.usecase

import com.example.mymovieapp.favorite_screen.domain.model.FavoritePerson
import com.example.mymovieapp.favorite_screen.domain.repository.FavoritePersonRepository
import javax.inject.Inject

class DeletePersonFavoriteUseCase @Inject constructor(private val repository: FavoritePersonRepository) {

    suspend fun execute(person: FavoritePerson) {
        repository.deletePerson(person = person)
    }
}