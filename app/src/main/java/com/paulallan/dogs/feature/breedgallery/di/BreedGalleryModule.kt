package com.paulallan.dogs.feature.breedgallery.di

import com.paulallan.dogs.feature.breedgallery.domain.GetRandomImagesForBreedUseCase
import com.paulallan.dogs.feature.breedgallery.presentation.BreedGalleryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val breedGalleryModule = module {
    single { GetRandomImagesForBreedUseCase(get()) }
    viewModel { BreedGalleryViewModel(get(), get()) }
}
