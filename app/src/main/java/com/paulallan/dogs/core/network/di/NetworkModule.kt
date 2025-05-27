package com.paulallan.dogs.core.network.di

import com.paulallan.dogs.core.network.ApiConstants
import com.paulallan.dogs.core.network.DogCeoApi
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DogCeoApi::class.java)

    }
}

