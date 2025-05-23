package com.paulallan.dogs.feature.breedlist.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.Coil.imageLoader
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import com.paulallan.dogs.R
import com.paulallan.dogs.app.theme.DoggyShowcaseTheme
import com.paulallan.dogs.feature.common.model.DogBreed
import com.paulallan.dogs.feature.common.presentation.LoadingSpinner
import java.util.Locale

@Composable
fun BreedListContent(
    state: BreedListViewState,
    onBreedClick: (String) -> Unit,
    imageLoader: ImageLoader,
    modifier: Modifier = Modifier,
) {
    when (state) {
        is BreedListViewState.Loading -> {
            LoadingSpinner()
        }

        is BreedListViewState.Success -> {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp)
            ) {
                items(state.dogBreeds, key = { it.name }) { dog ->
                    val displayName = remember {
                        dog.name.replaceFirstChar { it.uppercase(Locale.getDefault()) }
                    }
                    DogBreedListItem(
                        name = displayName,
                        imageUrl = dog.imageUrl,
                        imageLoader = imageLoader,
                        onClick = { onBreedClick(dog.name) }
                    )
                }
            }
        }

        is BreedListViewState.Error -> {
            val message = state.message
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "Error: $message",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.titleLarge,
                )
                HorizontalDivider(
                    modifier = Modifier
                        .padding(vertical = 32.dp)
                )
                Text(
                    text = "This would be a user presentable error message in a production app.",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
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
                .size(48.dp),
            contentScale = ContentScale.Fit
        )
        Text(
            text = stringResource(R.string.dog_breeds_title),
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
                .padding(end = 8.dp)
                .size(48.dp),
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
                        imageUrl = "https://images.dog.ceo/breeds/labrador/n02099712_5642.jpg"
                    ),
                    DogBreed(
                        name = "German Shepherd",
                        imageUrl = "https://images.dog.ceo/breeds/germanshepherd/n02106662_1234.jpg"
                    ),
                    DogBreed(
                        name = "Golden Retriever",
                        imageUrl = "https://images.dog.ceo/breeds/retriever-golden/n02099601_123.jpg"
                    )
                )
            ),
            onBreedClick = {},
            imageLoader = imageLoader(LocalContext.current)
        )
    }
}