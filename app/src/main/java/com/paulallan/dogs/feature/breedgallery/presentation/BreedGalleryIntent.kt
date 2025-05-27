package com.paulallan.dogs.feature.breedgallery.presentation

import com.paulallan.dogs.core.mvi.Intent

sealed class BreedGalleryIntent: Intent {
    data class LoadGallery(val breed: String) : BreedGalleryIntent()
    data class RefreshGallery(val breed: String) : BreedGalleryIntent()
}
