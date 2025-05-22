package com.paulallan.dogs.feature.breedlist.presentation

import androidx.lifecycle.viewModelScope
import com.paulallan.dogs.app.presentation.mvi.MVIViewModel
import com.paulallan.dogs.feature.breedlist.domain.GetBreedListUseCase
import kotlinx.coroutines.launch
import com.paulallan.dogs.core.result.Result

class BreedListViewModel(
    private val getDogBreedsUseCase: GetBreedListUseCase
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
                    try {
                        when (val result = getDogBreedsUseCase()) {
                            is Result.Success -> updateState {
                                BreedListViewState.Success(result.data)
                            }
                            is Result.Error -> updateState {
                                BreedListViewState.Error(result.message)
                            }
                        }
                    } catch (e: Exception) {
                        updateState {
                            BreedListViewState.Error(e.message ?: "Unknown error")
                        }
                    }
                }
            }
        }
    }
}
