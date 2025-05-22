package com.paulallan.dogs.core.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.paulallan.dogs.feature.breedgallery.presentation.BreedGalleryScreen
import com.paulallan.dogs.feature.breedlist.presentation.BreedListScreen

/*
* CompositionLocal to provide the NavController throughout the composable hierarchy.
* Helps avoid passing it explicitly through every screen.
* This is a clean and scalable approach for larger apps.
* */
val LocalNavController = staticCompositionLocalOf<NavController> {
    error("No NavController provided")
}

object Routes {
    const val BREEDS = "breeds"
    const val GALLERY = "gallery/{breedName}"
    fun galleryRoute(breedName: String) = "gallery/$breedName"
}

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(
            navController = navController,
            startDestination = Routes.BREEDS,
            modifier = modifier
        ) {
            composable(Routes.BREEDS) {
                BreedListScreen(
                    onBreedClick = { breed ->
                        navController.navigate(Routes.galleryRoute(breed))
                    }
                )
            }
            composable(Routes.GALLERY) { backStackEntry ->
                val breed = backStackEntry.arguments?.getString("breedName") ?: ""
                BreedGalleryScreen(breed = breed)
            }
        }
    }
}

