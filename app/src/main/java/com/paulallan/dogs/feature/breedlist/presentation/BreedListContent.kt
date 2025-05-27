@file:OptIn(ExperimentalMaterialApi::class)

package com.paulallan.dogs.feature.breedlist.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import coil.Coil.imageLoader
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import com.paulallan.dogs.R
import com.paulallan.dogs.app.theme.DoggyShowcaseTheme
import com.paulallan.dogs.feature.common.model.DogBreed
import com.paulallan.dogs.feature.common.presentation.ErrorContent
import com.paulallan.dogs.feature.common.presentation.LoadingSpinner
import org.koin.compose.koinInject

@Composable
fun BreedListContent(
    state: BreedListViewState,
    onBreedClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    imageLoader: ImageLoader = koinInject(),
    onRefresh: () -> Unit = {},
) {
    when (state) {
        is BreedListViewState.Loading -> {
            LoadingSpinner(
                modifier = modifier
                    .testTag("breed_list_loading"),
                spinnerSize = dimensionResource(id = R.dimen.spinner_size_large)
            )
        }

        is BreedListViewState.Success -> {
            val pullRefreshState = rememberPullRefreshState(
                refreshing = state.isRefreshing,
                onRefresh = onRefresh
            )

            Box(
                modifier = modifier
                    .fillMaxSize()
                    .pullRefresh(pullRefreshState)
                    .testTag("breed_list_success"),
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(
                        dimensionResource(id = R.dimen.paddingMedium)
                    ),
                    contentPadding = PaddingValues(
                        dimensionResource(id = R.dimen.paddingMedium)
                    )
                ) {
                    items(state.dogBreeds, key = { it.name }) { dog ->
                        val displayName = remember {
                            dog.displayName
                        }
                        DogBreedListItem(
                            name = displayName,
                            imageUrl = dog.imageUrl,
                            imageLoader = imageLoader,
                            onClick = { onBreedClick(dog.name) }
                        )
                    }
                }

                PullRefreshIndicator(
                    refreshing = state.isRefreshing,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
        }

        is BreedListViewState.Error -> {
            val scrollState = rememberScrollState()
            val pullRefreshState = rememberPullRefreshState(
                refreshing = state.isRefreshing,
                onRefresh = onRefresh
            )

            Box(
                modifier = modifier
                    .fillMaxSize()
                    .pullRefresh(pullRefreshState)
                    .verticalScroll(scrollState)
                    .testTag("breed_list_error"),
                contentAlignment = Center
            ) {
                ErrorContent(message = state.message)

                PullRefreshIndicator(
                    refreshing = state.isRefreshing,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
        }
    }
}

@Composable
fun BreedListContentTopAppBarTitleContent(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
    ) {
        Image(
            painter = rememberAsyncImagePainter(R.drawable.dog),
            contentDescription = null,
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.iconButtonSize)),
            contentScale = ContentScale.Fit
        )
        Text(
            text = stringResource(R.string.breed_list_title),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displaySmall,
            fontFamily = FontFamily.Cursive,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
        Image(
            painter = rememberAsyncImagePainter(R.drawable.dog),
            contentDescription = null,
            modifier = Modifier
                .padding(end = dimensionResource(id = R.dimen.paddingSmall))
                .size(dimensionResource(id = R.dimen.iconButtonSize)),
            contentScale = ContentScale.Fit
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BreedListContentPreview() {
    DoggyShowcaseTheme {
        BreedListContent(
            state = BreedListViewState.Success(
                dogBreeds = listOf(
                    DogBreed(
                        name = "Labrador Retriever",
                        displayName = "Labrador Retriever",
                        imageUrl = "https://images.dog.ceo/breeds/labrador/n02099712_5642.jpg"
                    ),
                    DogBreed(
                        name = "German Shepherd",
                        displayName = "German Shepherd",
                        imageUrl = "https://images.dog.ceo/breeds/germanshepherd/n02106662_1234.jpg"
                    ),
                    DogBreed(
                        name = "Golden Retriever",
                        displayName = "Golden Retriever",
                        imageUrl = "https://images.dog.ceo/breeds/retriever-golden/n02099601_123.jpg"
                    )
                ),
                isRefreshing = false
            ),
            onBreedClick = {},
            imageLoader = imageLoader(LocalContext.current),
            onRefresh = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BreedListContentRefreshingPreview() {
    DoggyShowcaseTheme {
        BreedListContent(
            state = BreedListViewState.Success(
                dogBreeds = listOf(
                    DogBreed(
                        name = "Labrador Retriever",
                        displayName = "Labrador Retriever",
                        imageUrl = "https://images.dog.ceo/breeds/labrador/n02099712_5642.jpg"
                    ),
                    DogBreed(
                        name = "German Shepherd",
                        displayName = "German Shepherd",
                        imageUrl = "https://images.dog.ceo/breeds/germanshepherd/n02106662_1234.jpg"
                    ),
                    DogBreed(
                        name = "Golden Retriever",
                        displayName = "Golden Retriever",
                        imageUrl = "https://images.dog.ceo/breeds/retriever-golden/n02099601_123.jpg"
                    )
                ),
                isRefreshing = true
            ),
            onBreedClick = {},
            imageLoader = imageLoader(LocalContext.current),
            onRefresh = {}
        )
    }
}
