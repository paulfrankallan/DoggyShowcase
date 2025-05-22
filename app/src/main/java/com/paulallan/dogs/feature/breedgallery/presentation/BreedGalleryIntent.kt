package com.paulallan.dogs.feature.breedgallery.presentation

import com.paulallan.dogs.app.presentation.mvi.Intent

sealed class BreedGalleryIntent: Intent {
    data class LoadGallery(val breed: String) : BreedGalleryIntent()
}
