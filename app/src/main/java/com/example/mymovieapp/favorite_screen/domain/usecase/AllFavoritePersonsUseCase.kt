package com.example.mymovieapp.favorite_screen.domain.usecase

import com.example.mymovieapp.favorite_screen.domain.models.FavoritePerson
import com.example.mymovieapp.favorite_screen.domain.repository.FavoritePersonRepository
import javax.inject.Inject

class AllFavoritePersonsUseCase @Inject constructor(private val repository: FavoritePersonRepository) {
    suspend fun execute(): List<FavoritePerson> = repository.allFavoritePersons()
}