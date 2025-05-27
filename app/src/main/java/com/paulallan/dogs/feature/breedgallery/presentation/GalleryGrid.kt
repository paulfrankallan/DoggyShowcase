package com.paulallan.dogs.feature.breedgallery.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import com.paulallan.dogs.R
import com.paulallan.dogs.feature.common.presentation.ImageComponent
import org.koin.compose.koinInject

@Composable
fun GalleryGrid(
    images: List<String>,
    modifier: Modifier = Modifier,
    imageLoader: ImageLoader = koinInject()
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    // Adjust columns and rows based on orientation
    val columns = if (isLandscape) 5 else 2
    val rows = if (isLandscape) 2 else 5

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.paddingSmall))
            .testTag("gallery_grid"),
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.paddingSmall)
        )
    ) {
        // Chunk the images into rows and columns
        // Each row will contain 'columns' number of images
        images.chunked(columns).take(rows).forEachIndexed { rowIndex, rowImages ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(
                    dimensionResource(id = R.dimen.paddingSmall)
                )
            ) {
                rowImages.forEachIndexed { colIndex, url ->
                    // Calculate the absolute index based on row and column
                    val itemIndex = rowIndex * columns + colIndex
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .testTag("gallery_item_$itemIndex"),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = dimensionResource(R.dimen.cardElevation)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            // Blurred image to provide a nice background effect
                            ImageComponent(
                                url = url,
                                contentDescription = "Dog image",
                                contentScale = ContentScale.Crop,
                                blur = true,
                                imageLoader = imageLoader,
                                modifier = Modifier
                                    .fillMaxSize()
                            )
                            // Main image displayed on top of the blurred background
                            ImageComponent(
                                url = url,
                                contentDescription = "Dog image",
                                contentScale = ContentScale.Fit,
                                imageLoader = imageLoader,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(dimensionResource(id = R.dimen.paddingSmall))
                            )
                        }
                    }
                }
            }
        }
    }
}
