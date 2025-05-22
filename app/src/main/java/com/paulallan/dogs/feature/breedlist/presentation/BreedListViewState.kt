package com.paulallan.dogs.feature.breedlist.presentation

import com.paulallan.dogs.app.presentation.mvi.ViewState
import com.paulallan.dogs.feature.common.model.DogBreed

sealed class BreedListViewState : ViewState {
    object Loading : BreedListViewState()
    data class Success(val dogBreeds: List<DogBreed>) : BreedListViewState()
    data class Error(val message: String) : BreedListViewState()
}