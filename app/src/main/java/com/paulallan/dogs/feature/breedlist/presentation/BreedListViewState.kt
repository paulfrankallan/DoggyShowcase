package com.paulallan.dogs.feature.breedlist.presentation

import com.paulallan.dogs.core.mvi.ViewState
import com.paulallan.dogs.feature.common.model.DogBreed

sealed class BreedListViewState : ViewState {
    object Loading : BreedListViewState()
    data class Success(
        val dogBreeds: List<DogBreed>,
        val isRefreshing: Boolean = false,
    ) : BreedListViewState()
    data class Error(
        val isRefreshing: Boolean = false,
        val message: String,
    ) : BreedListViewState()
}
