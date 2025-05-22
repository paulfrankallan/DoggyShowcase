package com.paulallan.dogs.feature.breedlist.presentation

import androidx.lifecycle.viewModelScope
import com.paulallan.dogs.app.presentation.mvi.MVIViewModel
import com.paulallan.dogs.feature.common.DogBreed
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BreedListViewModel(
) : MVIViewModel<BreedListViewState, BreedListIntent>(BreedListViewState.Loading) {
    override suspend fun initialDataLoad() {
        handleIntent(BreedListIntent.LoadDogs)
    }

    fun handleIntent(intent: BreedListIntent) {
        when (intent) {
            is BreedListIntent.LoadDogs -> {
                viewModelScope.launch {
                    updateState {
                        BreedListViewState.Loading
                    }
                    viewModelScope.launch {
                        // Simulate network delay
                        delay(2000)
                        updateState {
                            BreedListViewState.Success(
                                listOf(
                                    DogBreed("Bulldog", "https://example.com/bulldog.jpg"),
                                    DogBreed("Beagle", "https://example.com/beagle.jpg"),
                                    DogBreed("Poodle", "https://example.com/poodle.jpg"),
                                    DogBreed("Labrador", "https://example.com/labrador.jpg"),
                                    DogBreed("German Shepherd", "https://example.com/german_shepherd.jpg")
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}