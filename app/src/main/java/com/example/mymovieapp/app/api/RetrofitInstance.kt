package com.example.mymovieapp.app.api

import com.example.mymovieapp.app.utils.Utils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module()
@InstallIn(SingletonComponent::class)
object RetrofitInstance {

    @Singleton
    @Provides
    fun requestInterceptor() = Interceptor { chain ->
        val url = chain.request()
            .url
            .newBuilder()
            .addQueryParameter("api_key", Utils.API_KEY)
            .build()
        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()
        return@Interceptor chain.proceed(request)
    }

    @Singleton
    private fun okHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(requestInterceptor())
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Utils.BASE_URL)
            .client(okHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Singleton
    val movieApi: MovieApi by lazy {
        retrofit.create(MovieApi::class.java)
    }

    @Singleton
    val personApi: PersonApi by lazy {
        retrofit.create(PersonApi::class.java)
    }
}