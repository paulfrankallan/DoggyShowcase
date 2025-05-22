package com.paulallan.dogs.feature.breedlist.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paulallan.dogs.app.theme.DoggyShowcaseTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun BreedListScreen(
    modifier: Modifier = Modifier,
) {
    val viewModel: BreedListViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    BreedListContent(
        state = state,
        modifier = modifier,
    )
}

@Composable
fun BreedListContent(
    state: BreedListViewState,
    modifier: Modifier = Modifier,
) {
    when (state) {
        is BreedListViewState.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Center
            ) {
                CircularProgressIndicator()
            }
        }

        is BreedListViewState.Success -> {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp)
            ) {
                items(state.dogBreeds, key = { it.name }) { dog ->
                    Greeting(dog.name, modifier = modifier)
                }
            }
        }

        is BreedListViewState.Error -> {
            val message = state.message
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Center) {
                Text(text = "Error: $message", color = MaterialTheme.colorScheme.error)
            }
        }
    }
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