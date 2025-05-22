package com.paulallan.dogs.app.di

import com.paulallan.dogs.feature.breedlist.presentation.BreedListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { BreedListViewModel() }
}
