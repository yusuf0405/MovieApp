package com.example.mymovieapp.favorite_screen.domain.repository

import com.example.mymovieapp.favorite_screen.domain.model.FavoritePerson

interface FavoritePersonRepository {

    suspend fun addNewPerson(person: FavoritePerson)

    suspend fun deletePerson(person: FavoritePerson)

    suspend fun allFavoritePersons(): List<FavoritePerson>
}