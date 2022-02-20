package com.example.mymovieapp.app

import com.example.mymovieapp.favorite_screen.domain.model.FavoriteMovie
import com.example.mymovieapp.favorite_screen.domain.model.FavoritePerson
import com.example.mymovieapp.movie_details_screen.domain.model.MovieDetails
import com.example.mymovieapp.person_details_screen.domain.model.PersonDetails

internal fun MovieDetails.toFavoriteMovies(): FavoriteMovie {
    return FavoriteMovie(
        budget = budget,
        id = id,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate,
        revenue = revenue,
        runtime = runtime,
        status = status,
        tagline = tagline,
        title = title,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        voteCount = voteCount,
        video = video,
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
        imdbId = imdbId,
        knownForDepartment = knownForDepartment,
        name = name,
        placeOfBirth = placeOfBirth,
        popularity = popularity,
        profilePath = profilePath,
    )
}


