package com.paulallan.dogs.app.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.paulallan.dogs.app.theme.DoggyShowcaseTheme
import com.paulallan.dogs.core.nav.NavGraph
import com.paulallan.dogs.core.nav.Routes
import com.paulallan.dogs.feature.breedlist.presentation.BreedListScreen

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            DoggyShowcaseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavGraph(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}
