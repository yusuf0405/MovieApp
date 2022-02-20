package com.example.mymovieapp.favorite_screen.data.repository

import com.example.mymovieapp.favorite_screen.data.favor_person_db.FavoritePersonDao
import com.example.mymovieapp.favorite_screen.domain.model.FavoritePerson
import javax.inject.Inject

class FavoritePersonRepository @Inject constructor(private val personDao: FavoritePersonDao) {

    suspend fun addNewPerson(person: FavoritePerson) = personDao.addNewPerson(person = person)

    suspend fun deletePerson(person: FavoritePerson) = personDao.deletePerson(person = person)

    suspend fun allFavoritePersons() = personDao.getAllPerson()
}