package com.paulallan.dogs.feature.breedgallery.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import androidx.compose.ui.platform.LocalContext
import com.paulallan.dogs.feature.common.presentation.ImageComponent

@Composable
fun GalleryGrid(
    images: List<String>,
    modifier: Modifier = Modifier,
    imageLoader: ImageLoader = ImageLoader(LocalContext.current)
) {
    val columns = 2
    val rows = 5

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        images.chunked(columns).take(rows).forEach { rowImages ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowImages.forEach { url ->
                    Card(
                        modifier = Modifier
                            .weight(1f),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            ImageComponent(
                                url = url,
                                contentDescription = null ?: "Dog image",
                                contentScale = ContentScale.Crop,
                                blur = true,
                                imageLoader = imageLoader,
                                modifier = Modifier
                                    .fillMaxSize()
                            )
                            ImageComponent(
                                url = url,
                                contentDescription = null ?: "Dog image",
                                contentScale = ContentScale.Fit,
                                imageLoader = imageLoader,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
