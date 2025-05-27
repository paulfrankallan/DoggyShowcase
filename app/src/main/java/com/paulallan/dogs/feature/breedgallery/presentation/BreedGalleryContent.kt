@file:OptIn(ExperimentalMaterialApi::class)

package com.paulallan.dogs.feature.breedgallery.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.paulallan.dogs.R
import com.paulallan.dogs.feature.common.presentation.ErrorContent
import com.paulallan.dogs.feature.common.presentation.LoadingSpinner
import java.util.*

@Composable
fun BreedGalleryContent(
    state: BreedGalleryViewState,
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit = {},
) {
    val scrollState = rememberScrollState()
    when (state) {
        is BreedGalleryViewState.Loading -> {
            LoadingSpinner(
                modifier = modifier
                    .testTag("breed_gallery_loading"),
                spinnerSize = dimensionResource(id = R.dimen.spinner_size_large)
            )
        }

        is BreedGalleryViewState.Success -> {
            val pullRefreshState = rememberPullRefreshState(
                refreshing = state.isRefreshing,
                onRefresh = onRefresh
            )

            Box(
                modifier = modifier
                    .fillMaxSize()
                    .pullRefresh(pullRefreshState)
                    .testTag("breed_gallery_success"),
            ) {
                GalleryGrid(
                    images = state.imageUrls,
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState),
                )

                PullRefreshIndicator(
                    refreshing = state.isRefreshing,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
        }

        is BreedGalleryViewState.Error -> {
            val pullRefreshState = rememberPullRefreshState(
                refreshing = state.isRefreshing,
                onRefresh = onRefresh
            )

            Box(
                modifier = modifier
                    .fillMaxSize()
                    .pullRefresh(pullRefreshState)
                    .verticalScroll(scrollState)
                    .testTag("breed_gallery_error"),
                contentAlignment = Center
            ) {
                ErrorContent(message = state.message)
            }
        }
    }
}

@Composable
fun BreedGalleryTopAppBarTitleContent(
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
            style = MaterialTheme.typography.headlineLarge,
            fontFamily = FontFamily.Cursive,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.iconButtonSize)))
    }
}

@Preview(showBackground = true)
@Composable
fun BreedGalleryContentWith10ItemsPreview() {
    val sampleState = BreedGalleryViewState.Success(
        breed = "labrador",
        imageUrls = listOf(
            "https://images.dog.ceo/breeds/labrador/n02099712_5642.jpg",
            "https://images.dog.ceo/breeds/labrador/n02099712_3400.jpg",
            "https://images.dog.ceo/breeds/labrador/n02099712_1123.jpg",
            "https://images.dog.ceo/breeds/labrador/n02099712_1234.jpg",
            "https://images.dog.ceo/breeds/labrador/n02099712_5642.jpg",
            "https://images.dog.ceo/breeds/labrador/n02099712_3400.jpg",
            "https://images.dog.ceo/breeds/labrador/n02099712_1123.jpg",
            "https://images.dog.ceo/breeds/labrador/n02099712_1234.jpg",
            "https://images.dog.ceo/breeds/labrador/n02099712_1123.jpg",
            "https://images.dog.ceo/breeds/labrador/n02099712_1234.jpg",
        ),
        isRefreshing = false
    )
    BreedGalleryContent(state = sampleState)
}

@Preview(showBackground = true)
@Composable
fun BreedGalleryContentWith5ItemsPreview() {
    val sampleState = BreedGalleryViewState.Success(
        breed = "labrador",
        imageUrls = listOf(
            "https://images.dog.ceo/breeds/labrador/n02099712_5642.jpg",
            "https://images.dog.ceo/breeds/labrador/n02099712_3400.jpg",
            "https://images.dog.ceo/breeds/labrador/n02099712_1123.jpg",
            "https://images.dog.ceo/breeds/labrador/n02099712_1234.jpg",
            "https://images.dog.ceo/breeds/labrador/n02099712_5642.jpg",
        ),
        isRefreshing = false
    )
    BreedGalleryContent(state = sampleState)
}
