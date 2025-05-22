package com.paulallan.dogs.feature.breedgallery.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier

@Composable
fun BreedGalleryScreen(
    modifier: Modifier = Modifier,
    breed: String,
) {

    BreedGalleryContent(modifier)
}

@Composable
fun BreedGalleryContent(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Center
    ) {
        Text(
            text = "Breed Gallery",
            modifier = modifier
        )
    }
}
