package com.example.mymovieapp.app.utils

import com.example.mymovieapp.screen_favorite.domain.models.FavoriteMovie
import com.example.mymovieapp.screen_favorite.domain.models.FavoritePerson
import com.example.mymovieapp.screen_movie_details.domain.models.MovieDetails
import com.example.mymovieapp.screen_person_details.domain.models.PersonDetails

internal fun MovieDetails.toFavoriteMovies(): FavoriteMovie {
    return FavoriteMovie(
        id = id,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        rating = rating,
    )
}

internal fun PersonDetails.toFavoritePerson(): FavoritePerson {
    return FavoritePerson(
        adult = adult,
        biography = biography,
        birthday = biography,
        gender = gender,
        id = id,
        knownForDepartment = knownForDepartment,
        name = name,
        placeOfBirth = placeOfBirth,
        popularity = popularity,
        profilePath = profilePath,
    )
}


