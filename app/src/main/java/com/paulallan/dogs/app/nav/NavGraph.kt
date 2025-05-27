package com.paulallan.dogs.app.nav

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
                        navController.navigate(Routes.galleryRoute(Uri.encode(breed)))
                    }
                )
            }
            composable(
                route = "gallery/{breedName}",
                arguments = listOf(navArgument("breedName") { type = NavType.StringType })
            ) { backStackEntry ->
                val breed = Uri.decode(backStackEntry.arguments?.getString("breedName") ?: "")
                BreedGalleryScreen(breed = breed)
            }
        }
    }
}

