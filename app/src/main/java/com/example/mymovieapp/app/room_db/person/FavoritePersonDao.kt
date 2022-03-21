package com.example.mymovieapp.app.room_db.person

import androidx.room.*
import com.example.mymovieapp.screen_favorite.domain.models.FavoritePerson

@Dao
interface FavoritePersonDao {

    @Insert
    suspend fun addNewPerson(person: FavoritePerson)

    @Update
    suspend fun updatePerson(person: FavoritePerson)

    @Delete
    suspend fun deletePerson(person: FavoritePerson)

    @Query("select * from favor_person_database")
    suspend fun getAllPerson(): MutableList<FavoritePerson>

    @Query("select * from favor_person_database where id == :personId")
    suspend fun getPerson(personId: Int): FavoritePerson
}
