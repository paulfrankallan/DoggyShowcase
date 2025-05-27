package com.paulallan.dogs.feature.breedgallery.presentation

import androidx.lifecycle.viewModelScope
import com.paulallan.dogs.core.mvi.MVIViewModel
import com.paulallan.dogs.core.network.ApiConstants
import com.paulallan.dogs.core.outcome.Outcome
import com.paulallan.dogs.feature.breedgallery.domain.GetRandomImagesForBreedUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ViewModel for managing the state and logic of the breed gallery screen.
 *
 * Handles loading and refreshing random images for a specific dog breed using the provided use case.
 *
 * @property getRandomImagesForBreedUseCase Use case to fetch random images for a breed.
 * @property breed The breed name for which images are displayed.
 * @property dispatcher Coroutine dispatcher for background operations (defaults to [Dispatchers.IO]).
 */
class BreedGalleryViewModel(
    private val getRandomImagesForBreedUseCase: GetRandomImagesForBreedUseCase,
    private val breed: String,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : MVIViewModel<BreedGalleryViewState, BreedGalleryIntent>(BreedGalleryViewState.Loading) {
    /**
     * Called when the state is first collected.
     * Triggers loading of the gallery for the current breed.
     */
    override suspend fun initialDataLoad() {
        handleIntent(BreedGalleryIntent.LoadGallery(breed))
    }

    /**
     * Handles gallery-related intents such as loading or refreshing images.
     *
     * @param intent The [BreedGalleryIntent] to handle.
     */
    fun handleIntent(intent: BreedGalleryIntent) {
        when (intent) {
            is BreedGalleryIntent.LoadGallery -> {
                loadBreedGallery(intent.breed, isRefreshing = false)
            }
            is BreedGalleryIntent.RefreshGallery -> {
                loadBreedGallery(intent.breed, isRefreshing = true)
            }
        }
    }

    /**
     * Loads random images for the given breed and updates the view state accordingly.
     *
     * @param breed The breed name to load images for.
     * @param isRefreshing Whether this load is part of a refresh operation.
     */
    private fun loadBreedGallery(breed: String, isRefreshing: Boolean) {
        viewModelScope.launch(dispatcher) {
            // If refreshing, keep the current state but set isRefreshing to true
            if (isRefreshing) {
                val currentState = state.value
                if (currentState is BreedGalleryViewState.Success) {
                    updateState {
                        currentState.copy(isRefreshing = true)
                    }
                } else {
                    updateState {
                        BreedGalleryViewState.Loading
                    }
                }
            } else {
                updateState {
                    BreedGalleryViewState.Loading
                }
            }
            try {
                when (
                    val outcome = getRandomImagesForBreedUseCase(
                        breed = breed,
                        count = ApiConstants.DEFAULT_RANDOM_DOG_IMAGE_COUNT
                    )
                ) {
                    is Outcome.Success -> updateState {
                        BreedGalleryViewState.Success(
                            breed = breed,
                            imageUrls = outcome.data,
                            isRefreshing = false
                        )
                    }
                    is Outcome.Error -> updateState {
                        BreedGalleryViewState.Error(outcome.message)
                    }
                }
            } catch (e: Exception) {
                updateState {
                    BreedGalleryViewState.Error(e.message ?: "Unknown error")
                }
            }
        }
    }
}
