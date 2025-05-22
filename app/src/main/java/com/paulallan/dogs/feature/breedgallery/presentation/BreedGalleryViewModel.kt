package com.paulallan.dogs.feature.breedgallery.presentation

import androidx.lifecycle.viewModelScope
import com.paulallan.dogs.app.presentation.mvi.MVIViewModel
import com.paulallan.dogs.core.network.ApiConstants
import com.paulallan.dogs.core.result.Result
import com.paulallan.dogs.feature.breedgallery.domain.GetRandomImagesForBreedUseCase
import kotlinx.coroutines.launch

class BreedGalleryViewModel(
    private val getRandomImagesForBreedUseCase: GetRandomImagesForBreedUseCase
) : MVIViewModel<BreedGalleryViewState, BreedGalleryIntent>(BreedGalleryViewState.Loading) {
    fun handleIntent(intent: BreedGalleryIntent) {
        when (intent) {
            is BreedGalleryIntent.LoadGallery -> {
                viewModelScope.launch {
                    updateState { BreedGalleryViewState.Loading }
                    when (
                        val result = getRandomImagesForBreedUseCase(
                            breed = intent.breed,
                            count = ApiConstants.DEFAULT_RANDOM_DOG_IMAGE_COUNT
                        )
                    ) {
                        is Result.Success -> updateState {
                            BreedGalleryViewState.Success(result.data)
                        }

                        is Result.Error -> updateState {
                            BreedGalleryViewState.Error(result.message)
                        }
                    }
                }
            }
        }
    }
}
