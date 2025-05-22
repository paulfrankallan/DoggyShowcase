package com.paulallan.dogs.feature.breedlist.presentation

import com.paulallan.dogs.app.presentation.mvi.Intent

sealed class BreedListIntent : Intent {
    object LoadDogs : BreedListIntent()
}