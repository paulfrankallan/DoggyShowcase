package com.paulallan.dogs.feature.breedlist.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import coil.ImageLoader
import com.paulallan.dogs.R
import com.paulallan.dogs.feature.common.presentation.ImageComponent

@Composable
fun DogBreedListItem(
    name: String,
    imageUrl: String?,
    imageLoader: ImageLoader,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("breed_list_item")
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(
            defaultElevation = dimensionResource(R.dimen.cardElevation)
        ),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.paddingMedium))
        ) {
            imageUrl?.let {
                ImageComponent(
                    url = it,
                    contentDescription = name,
                    coverWidth = dimensionResource(id = R.dimen.listThumbnailSize),
                    coverHeight = dimensionResource(id = R.dimen.listThumbnailSize),
                    contentScale = ContentScale.Crop,
                    imageLoader = imageLoader
                )
            }
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.paddingMedium)))
            Text(
                text = name,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
