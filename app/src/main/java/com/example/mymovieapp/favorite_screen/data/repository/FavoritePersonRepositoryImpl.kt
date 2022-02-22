package com.example.mymovieapp.favorite_screen.data.repository

import com.example.mymovieapp.favorite_screen.data.favor_person_db.FavoritePersonDao
import com.example.mymovieapp.favorite_screen.domain.model.FavoritePerson
import com.example.mymovieapp.favorite_screen.domain.repository.FavoritePersonRepository
import javax.inject.Inject

class FavoritePersonRepositoryImpl @Inject constructor(private val personDao: FavoritePersonDao) :
    FavoritePersonRepository {

    override suspend fun addNewPerson(person: FavoritePerson) =
        personDao.addNewPerson(person = person)

    override suspend fun deletePerson(person: FavoritePerson) =
        personDao.deletePerson(person = person)

    override suspend fun allFavoritePersons() = personDao.getAllPerson()
}