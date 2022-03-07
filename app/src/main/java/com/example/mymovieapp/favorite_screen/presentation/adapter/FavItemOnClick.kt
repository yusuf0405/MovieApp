package com.example.mymovieapp.favorite_screen.presentation.adapter

import com.example.mymovieapp.favorite_screen.domain.models.FavoriteMovie
import com.example.mymovieapp.favorite_screen.domain.models.FavoritePerson

interface FavItemOnClick {
    fun deleteMovie(movie: FavoriteMovie)
    fun deletePerson(person: FavoritePerson)
}