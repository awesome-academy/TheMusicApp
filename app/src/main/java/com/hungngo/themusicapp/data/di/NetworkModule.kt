package com.hungngo.themusicapp.data.di

import com.hungngo.themusicapp.BuildConfig
import com.hungngo.themusicapp.data.source.remote.api.ApiService
import com.hungngo.themusicapp.utils.Constant
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val NetworkModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(get()) }
    single { provideApiService(get()) }
}

fun provideOkHttpClient(): OkHttpClient {
    val httpClientBuilder = OkHttpClient.Builder()

    val logging = HttpLoggingInterceptor()
    logging.setLevel(HttpLoggingInterceptor.Level.BODY)

    httpClientBuilder
        .addInterceptor(Interceptor {
            val originalRequest = it.request()
            val newRequest = originalRequest.newBuilder().apply {
                header("X-RapidAPI-Host", Constant.API_HOST)
                header("X-RapidAPI-Key", BuildConfig.API_KEY)
            }.build()
            return@Interceptor it.proceed(newRequest)
        })
        .addInterceptor(logging)
        .build()
    return httpClientBuilder.build()
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(Constant.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
}

fun provideApiService(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
}
