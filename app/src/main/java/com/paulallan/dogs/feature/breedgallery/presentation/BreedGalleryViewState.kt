package com.paulallan.dogs.feature.breedgallery.presentation

import com.paulallan.dogs.app.presentation.mvi.ViewState

sealed class BreedGalleryViewState: ViewState {
    object Loading : BreedGalleryViewState()
    data class Success(val imageUrls: List<String>) : BreedGalleryViewState()
    data class Error(val message: String) : BreedGalleryViewState()
}
