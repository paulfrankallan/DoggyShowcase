package com.paulallan.dogs.feature.common.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import com.paulallan.dogs.R

@Composable
fun LoadingSpinner(
    modifier: Modifier = Modifier,
    spinnerSize: Dp = dimensionResource(id = R.dimen.spinner_size_small),
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(spinnerSize)
                .testTag("loading_spinner"),
            trackColor = MaterialTheme.colorScheme.onPrimary,
        )
    }
}
