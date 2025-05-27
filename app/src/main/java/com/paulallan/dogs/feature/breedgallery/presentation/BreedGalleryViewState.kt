package com.paulallan.dogs.feature.breedgallery.presentation

import com.paulallan.dogs.core.mvi.ViewState

sealed class BreedGalleryViewState: ViewState {
    object Loading : BreedGalleryViewState()
    data class Success(
        val breed: String,
        val imageUrls: List<String>,
        val isRefreshing: Boolean = false
    ) : BreedGalleryViewState()
    data class Error(
        val message: String,
        val isRefreshing: Boolean = false
    ) : BreedGalleryViewState()
}
