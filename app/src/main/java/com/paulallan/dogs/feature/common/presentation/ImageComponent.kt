package com.paulallan.dogs.feature.common.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.paulallan.dogs.R

@Composable
fun ImageComponent(
    url: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
    coverWidth: Dp? = null,
    coverHeight: Dp? = null,
    contentScale: ContentScale = ContentScale.Crop,
    blur: Boolean = false,
    blurAmount: Dp = 10.dp,
    imageLoader: ImageLoader
) {
    val density = LocalDensity.current
    val widthPx = coverWidth?.let { with(density) { it.roundToPx() } }
    val heightPx = coverHeight?.let { with(density) { it.roundToPx() } }

    val imageModifier = modifier
        .then(
            if (coverWidth != null && coverHeight != null) {
                Modifier.size(width = coverWidth, height = coverHeight)
            } else Modifier
        )
        .clip(MaterialTheme.shapes.small)
        .let { if (blur) it.blur(blurAmount) else it }

    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .apply {
                if (widthPx != null && heightPx != null) {
                    size(width = widthPx, height = heightPx)
                }
            }
            .build(),
        imageLoader = imageLoader,
        contentDescription = contentDescription.ifBlank {
            stringResource(R.string.image_content_description_default)
        },
        contentScale = contentScale,
        modifier = imageModifier,
        loading = {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                LoadingSpinner()
            }
        },
        error = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.error_oops),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    )
}
