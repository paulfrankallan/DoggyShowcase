@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)

package com.paulallan.dogs.feature.breedlist.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.Coil.imageLoader
import coil.ImageLoader
import org.koin.androidx.compose.koinViewModel

@Composable
fun BreedListScreen(
    modifier: Modifier = Modifier,
    viewModel: BreedListViewModel = koinViewModel(),
    onBreedClick: (String) -> Unit = {},
) {
    val state by viewModel.state.collectAsState()

    val onRefresh = { viewModel.handleIntent(BreedListIntent.RefreshBreeds) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.primary,
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    BreedListContentTopAppBarTitleContent()
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        BreedListContent(
            state = state,
            onBreedClick = onBreedClick,
            modifier = modifier.padding(paddingValues),
            onRefresh = onRefresh
        )
    }
}
