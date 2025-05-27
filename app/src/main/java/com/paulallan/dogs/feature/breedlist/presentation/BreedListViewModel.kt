package com.paulallan.dogs.feature.breedlist.presentation

import androidx.lifecycle.viewModelScope
import com.paulallan.dogs.core.mvi.MVIViewModel
import com.paulallan.dogs.core.outcome.Outcome
import com.paulallan.dogs.feature.breedlist.domain.GetBreedListUseCase
import kotlinx.coroutines.launch

/**
 * ViewModel for managing the state and logic of the dog breed list screen.
 *
 * Handles loading the list of dog breeds using the provided use case and updates the view state accordingly.
 *
 * @property getDogBreedsUseCase Use case to fetch the list of dog breeds.
 */
class BreedListViewModel(
    private val getDogBreedsUseCase: GetBreedListUseCase
) : MVIViewModel<BreedListViewState, BreedListIntent>(
    initialState = BreedListViewState.Loading
) {
    /**
     * Called when the state is first collected.
     * Triggers loading of the dog breeds list.
     */
    override suspend fun initialDataLoad() {
        handleIntent(BreedListIntent.LoadBreeds)
    }

    /**
     * Handles intents related to the breed list, such as loading the list of breeds.
     *
     * @param intent The [BreedListIntent] to handle.
     */
    fun handleIntent(intent: BreedListIntent) {
        when (intent) {
            is BreedListIntent.LoadBreeds -> {
                loadBreedList(isRefreshing = false)
            }
            is BreedListIntent.RefreshBreeds -> {
                loadBreedList(isRefreshing = true)
            }
        }
    }

    /**
     * Loads the list of dog breeds and updates the view state accordingly.
     *
     * @param isRefreshing Whether this load is part of a refresh operation.
     */
    private fun loadBreedList(isRefreshing: Boolean) {
        viewModelScope.launch {
            // If refreshing, keep the current state but set isRefreshing to true
            if (isRefreshing) {
                val currentState = state.value
                if (currentState is BreedListViewState.Success) {
                    updateState {
                        currentState.copy(isRefreshing = true)
                    }
                } else {
                    updateState {
                        BreedListViewState.Loading
                    }
                }
            } else {
                updateState {
                    BreedListViewState.Loading
                }
            }

            try {
                when (val outcome = getDogBreedsUseCase()) {
                    is Outcome.Success -> updateState {
                        BreedListViewState.Success(
                            dogBreeds = outcome.data,
                            isRefreshing = false
                        )
                    }
                    is Outcome.Error -> updateState {
                        BreedListViewState.Error(message = outcome.message)
                    }
                }
            } catch (e: Exception) {
                updateState {
                    BreedListViewState.Error(message = e.message ?: "Unknown error")
                }
            }
        }
    }
}
