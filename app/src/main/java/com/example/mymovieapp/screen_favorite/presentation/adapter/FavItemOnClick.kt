package com.example.mymovieapp.screen_favorite.presentation.adapter

import com.example.mymovieapp.screen_favorite.domain.models.FavoriteMovie
import com.example.mymovieapp.screen_favorite.domain.models.FavoritePerson

interface FavItemOnClick {
    fun deleteMovie(movie: FavoriteMovie)
    fun deletePerson(person: FavoritePerson)
}