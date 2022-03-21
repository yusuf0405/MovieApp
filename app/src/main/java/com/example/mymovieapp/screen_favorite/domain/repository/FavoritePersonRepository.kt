package com.example.mymovieapp.screen_favorite.domain.repository

import com.example.mymovieapp.screen_favorite.domain.models.FavoritePerson

interface FavoritePersonRepository {

    suspend fun addNewPerson(person: FavoritePerson)

    suspend fun deletePerson(person: FavoritePerson)

    suspend fun allFavoritePersons(): List<FavoritePerson>

    suspend fun getFavoritePerson(id: Int): FavoritePerson
}