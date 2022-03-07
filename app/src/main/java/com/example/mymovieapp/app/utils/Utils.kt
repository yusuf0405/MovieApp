package com.example.mymovieapp.app.utils

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Utils {
    companion object {
        const val API_KEY = "6e63c2317fbe963d76c3bdc2b785f6d1"
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342"
        const val MAX_PAGE_SIZE = 20
        const val DEFAULT_PAGE_SIZE = 1
        const val MOVIE_ID_KEY = "MOVIE_ID_KEY"
        const val PERSON_ID_KEY = "PERSON_ID_KEY"
    }
}