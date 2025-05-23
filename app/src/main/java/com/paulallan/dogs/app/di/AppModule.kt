package com.paulallan.dogs.app.di

import com.paulallan.dogs.core.network.ApiConstants
import com.paulallan.dogs.core.network.DogCeoApi
import com.paulallan.dogs.feature.breedgallery.domain.GetRandomImagesForBreedUseCase
import com.paulallan.dogs.feature.breedgallery.presentation.BreedGalleryViewModel
import com.paulallan.dogs.feature.breedlist.domain.GetBreedListUseCase
import com.paulallan.dogs.feature.breedlist.presentation.BreedListViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DogCeoApi::class.java)
    }
    single { GetBreedListUseCase(get()) }
    single { GetRandomImagesForBreedUseCase(get()) }
    viewModel { BreedListViewModel(get()) }
    viewModel { BreedGalleryViewModel(get()) }
}
