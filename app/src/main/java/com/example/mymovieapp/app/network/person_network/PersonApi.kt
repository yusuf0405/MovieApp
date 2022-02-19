package com.example.mymovieapp.app.network.person_network

import androidx.annotation.IntRange
import com.example.mymovieapp.person_screen.domain.model.PersonResponse
import com.example.mymovieapp.person_details_screen.domain.model.MovieCredits
import com.example.mymovieapp.person_details_screen.domain.model.PersonDetails
import com.example.mymovieapp.app.utils.Utils.Companion.DEFAULT_PAGE_SIZE
import com.example.mymovieapp.app.utils.Utils.Companion.MAX_PAGE_SIZE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PersonApi {

    @GET("person/popular")
    suspend fun getPopularPerson(
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("pageSize") @IntRange(from = 1,
            to = MAX_PAGE_SIZE.toLong()) pageSize: Int = DEFAULT_PAGE_SIZE,
    ): Response<PersonResponse>

    @GET("search/person")
    suspend fun getSearchPerson(
        @Query("query") query: String,
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("pageSize") @IntRange(from = 1,
            to = MAX_PAGE_SIZE.toLong()) pageSize: Int = DEFAULT_PAGE_SIZE,
    ): Response<PersonResponse>

    @GET("person/{person_id}")
    suspend fun getPersonDetails(@Path("person_id") id: Int): Response<PersonDetails>

    @GET("person/{person_id}/movie_credits")
    suspend fun getPersonCreditMovies(@Path("person_id") id: Int): Response<MovieCredits>

}
