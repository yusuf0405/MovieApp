package com.example.mymovieapp.app.di

import com.example.mymovieapp.app.api.MovieApi
import com.example.mymovieapp.app.api.PersonApi
import com.example.mymovieapp.app.utils.Cons.Companion.API_KEY
import com.example.mymovieapp.app.utils.Cons.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {
    @Provides
    @Singleton
    fun requestInterceptor() = Interceptor { cain ->
        val url = cain.request()
            .url
            .newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()
        val request = cain.request()
            .newBuilder()
            .cacheControl(CacheControl.Builder().maxAge(0,TimeUnit.SECONDS).build())
            .url(url)
            .build()
        return@Interceptor cain.proceed(request)

    }

    @Provides
    @Singleton
    fun httpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun okHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        interceptor: Interceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(timeout = 30, TimeUnit.SECONDS)
            .readTimeout(timeout = 30, TimeUnit.SECONDS)
            .writeTimeout(timeout = 30, TimeUnit.SECONDS)
            .build()
    }


    @Provides
    @Singleton
    fun retrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    @Provides
    @Singleton
    fun movieApi(retrofit: Retrofit): MovieApi =
        retrofit.create(MovieApi::class.java)

    @Provides
    @Singleton
    fun personApi(retrofit: Retrofit): PersonApi =
        retrofit.create(PersonApi::class.java)
}