package com.example.mymovieapp.screen_favorite.domain.usecase

import com.example.mymovieapp.screen_favorite.domain.models.FavoritePerson
import com.example.mymovieapp.screen_favorite.domain.repository.FavoritePersonRepository
import javax.inject.Inject

class GetFavoritePersonUseCase @Inject constructor(private val repository: FavoritePersonRepository) {
    suspend fun execute(id: Int): FavoritePerson = repository.getFavoritePerson(id = id)

}