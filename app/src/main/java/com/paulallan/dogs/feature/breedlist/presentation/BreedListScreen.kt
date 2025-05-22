package com.paulallan.dogs.feature.breedlist.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.paulallan.dogs.app.theme.DoggyShowcaseTheme

@Composable
fun BreedListScreen(
    modifier: Modifier = Modifier,
) {
    BreedListContent(modifier = modifier)
}

@Composable
fun BreedListContent(
    modifier: Modifier = Modifier,
) {
    Greeting("Dogs!", modifier)
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Center
    ) {
        Text(
            text = "Hello $name!",
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BreedListScreenPreview() {
    DoggyShowcaseTheme {
        BreedListScreen()
    }
}