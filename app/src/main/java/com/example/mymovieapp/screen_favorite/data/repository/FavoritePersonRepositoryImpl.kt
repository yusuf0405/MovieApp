package com.example.mymovieapp.screen_favorite.data.repository

import com.example.mymovieapp.app.room_db.person.FavoritePersonDao
import com.example.mymovieapp.screen_favorite.domain.models.FavoritePerson
import com.example.mymovieapp.screen_favorite.domain.repository.FavoritePersonRepository
import javax.inject.Inject

class FavoritePersonRepositoryImpl @Inject constructor(private val personDao: FavoritePersonDao) :
    FavoritePersonRepository {

    override suspend fun addNewPerson(person: FavoritePerson) =
        personDao.addNewPerson(person = person)

    override suspend fun deletePerson(person: FavoritePerson) =
        personDao.deletePerson(person = person)

    override suspend fun allFavoritePersons() = personDao.getAllPerson()

    override suspend fun getFavoritePerson(id: Int): FavoritePerson =
        personDao.getPerson(personId = id)
}