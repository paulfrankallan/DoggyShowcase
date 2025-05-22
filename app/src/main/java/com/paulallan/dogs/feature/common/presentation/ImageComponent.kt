package com.paulallan.dogs.feature.common.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
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
    coverWidthDp: Int? = null,
    coverHeightDp: Int? = null,
    contentScale: ContentScale = ContentScale.Crop,
    blur: Boolean = false,
    imageLoader: ImageLoader,
) {
    val density = LocalDensity.current
    val widthPx = remember(coverWidthDp, density) { coverWidthDp?.let { (it * density.density).toInt() } }
    val heightPx = remember(coverHeightDp, density) { coverHeightDp?.let { (it * density.density).toInt() } }
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .apply {
                if (widthPx != null && heightPx != null) size(width = widthPx, height = heightPx)
            }
            .build(),
        imageLoader = imageLoader,
        contentDescription = contentDescription, // TODO add a formatted string?
        contentScale = contentScale,
        loading = {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 3.dp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.loading),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
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
        },
        modifier = modifier
            .then(
                if (coverWidthDp != null && coverHeightDp != null) {
                    Modifier.size(width = coverWidthDp.dp, height = coverHeightDp.dp)
                } else Modifier
            )
            .clip(MaterialTheme.shapes.small)
            .let { if (blur) it.blur(10.dp) else it }
    )
}
