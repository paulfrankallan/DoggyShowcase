@file:OptIn(ExperimentalMaterial3Api::class)

package com.paulallan.dogs.feature.breedgallery.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.paulallan.dogs.core.nav.LocalNavController
import org.koin.androidx.compose.koinViewModel
import java.util.Locale

private val IconButtonSize = 48.dp

@Composable
fun BreedGalleryScreen(
    modifier: Modifier = Modifier,
    viewModel: BreedGalleryViewModel = koinViewModel(),
    breed: String,
) {
    val navController = LocalNavController.current
    val state by viewModel.state.collectAsState()

    LaunchedEffect(breed) {
        viewModel.handleIntent(BreedGalleryIntent.LoadGallery(breed))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    TopAppBarTitleContent(breed)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.primary,
        modifier = modifier
    ) { innerPadding ->
        BreedGalleryContent(
            state = state,
            modifier = modifier.padding(innerPadding)
        )
    }
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
            GalleryGrid(
                images = state.imageUrls,
                modifier = modifier
                    .fillMaxSize()
            )
        }

        is BreedGalleryViewState.Error -> {
            val message = state.message
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Center) {
                Text(text = "Error: $message", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Composable
fun TopAppBarTitleContent(
    breed: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = breed.replaceFirstChar { it.uppercase(Locale.getDefault()) },
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displaySmall,
            fontFamily = FontFamily.Cursive,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.width(IconButtonSize))
    }
}