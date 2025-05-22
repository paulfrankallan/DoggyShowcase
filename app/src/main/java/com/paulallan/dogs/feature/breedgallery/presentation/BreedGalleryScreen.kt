package com.paulallan.dogs.feature.breedgallery.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel

@Composable
fun BreedGalleryScreen(
    modifier: Modifier = Modifier,
    viewModel: BreedGalleryViewModel = koinViewModel(),
    breed: String,
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(breed) {
        viewModel.handleIntent(BreedGalleryIntent.LoadGallery(breed))
    }

    BreedGalleryContent(
        state = state,
        modifier = modifier
    )
}

@Composable
fun BreedGalleryContent(
    state: BreedGalleryViewState,
    modifier: Modifier = Modifier,
) {
    when (state) {
        is BreedGalleryViewState.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Center
            ) {
                CircularProgressIndicator()
            }
        }

        is BreedGalleryViewState.Success -> {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp)
            ) {
                items(state.images, key = { it }) { dog ->
                    Text(
                        text = dog
                    )
                }
            }
        }

        is BreedGalleryViewState.Error -> {
            val message = state.message
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Center) {
                Text(text = "Error: $message", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}
