@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)

package com.paulallan.dogs.feature.breedgallery.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.paulallan.dogs.R
import com.paulallan.dogs.core.nav.LocalNavController
import org.koin.androidx.compose.koinViewModel

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

    val onRefresh = { viewModel.handleIntent(BreedGalleryIntent.RefreshGallery(breed)) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    BreedGalleryTopAppBarTitleContent(breed)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back),
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
            modifier = modifier.padding(innerPadding),
            onRefresh = onRefresh
        )
    }
}
