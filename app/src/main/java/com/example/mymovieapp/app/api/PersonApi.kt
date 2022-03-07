package com.example.mymovieapp.app.api

import androidx.annotation.IntRange
import com.example.mymovieapp.app.utils.Utils.Companion.API_KEY
import com.example.mymovieapp.app.utils.Utils.Companion.BASE_URL
import com.example.mymovieapp.app.utils.Utils.Companion.DEFAULT_PAGE_SIZE
import com.example.mymovieapp.app.utils.Utils.Companion.MAX_PAGE_SIZE
import com.example.mymovieapp.person_details_screen.domain.models.MovieCredits
import com.example.mymovieapp.person_details_screen.domain.models.PersonDetails
import com.example.mymovieapp.person_screen.domain.models.PersonResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

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

    fun requestInterceptor() = Interceptor { chain ->
        val url = chain.request()
            .url
            .newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()
        val request = chain.request()
            .newBuilder()
            .url(url = url)
            .build()
        return@Interceptor chain.proceed(request = request)
    }



    fun okHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        request: Interceptor,
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(requestInterceptor())
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

    fun retrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


}
