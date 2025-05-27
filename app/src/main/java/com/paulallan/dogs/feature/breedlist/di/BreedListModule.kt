package com.paulallan.dogs.feature.breedlist.di

import com.paulallan.dogs.feature.breedlist.domain.GetBreedListUseCase
import com.paulallan.dogs.feature.breedlist.presentation.BreedListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val breedListModule = module {
    single { GetBreedListUseCase(get()) }
    viewModel { BreedListViewModel(get()) }
}
