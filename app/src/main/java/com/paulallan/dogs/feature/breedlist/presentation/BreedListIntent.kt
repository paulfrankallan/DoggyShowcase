package com.paulallan.dogs.feature.breedlist.presentation

import com.paulallan.dogs.core.mvi.Intent

sealed class BreedListIntent : Intent {
    object LoadBreeds : BreedListIntent()
    object RefreshBreeds : BreedListIntent()
}
