package com.paulallan.dogs.feature.breedlist.domain

import com.paulallan.dogs.core.network.DogCeoApi
import com.paulallan.dogs.core.network.requireSuccess
import com.paulallan.dogs.core.outcome.Outcome
import com.paulallan.dogs.feature.common.model.DogBreed
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.util.*

/**
 * Use case for fetching the list of all dog breeds and their images from the Dog CEO API.
 *
 * This use case retrieves all parent breeds and their sub-breeds, flattens them into a single list,
 * and fetches a random image for each breed or sub-breed concurrently.
 *
 * @property api The Dog CEO API client used to fetch breed data and images.
 */
class GetBreedListUseCase(private val api: DogCeoApi) {

    /**
     * Invokes the use case to fetch the list of dog breeds with their images.
     *
     * @return [Outcome] containing a list of [DogBreed] on success, or an error message on failure.
     */
    suspend operator fun invoke(): Outcome<List<DogBreed>> = coroutineScope {
        try {
            // Fetch all breeds and ensure the response is successful
            val breedListResponse = api.getAllBreeds()
            val breedsMap = breedListResponse.requireSuccess(breedListResponse.message)

            // Create a flat list to hold all breeds and sub-breeds as strings
            val allBreeds = mutableListOf<String>()

            // Process parent breeds and their sub-breeds
            breedsMap.forEach { (parentBreed, subBreeds) ->
                allBreeds.add(parentBreed)
                subBreeds.forEach { subBreed ->
                    allBreeds.add("$parentBreed/$subBreed")
                }
            }

            // For each breed and sub-breed, fetch a random image concurrently
            val dogBreeds = allBreeds.map { breed ->
                async {
                    // Fetch a random image for the breed and ensure the response is successful
                    val imageUrlResponse = api.getRandomImageForBreed(breed)
                    val imageUrl = imageUrlResponse.requireSuccess(imageUrlResponse.message)

                    DogBreed(
                        name = breed,
                        displayName = breed.replaceFirstChar { it.uppercase(Locale.getDefault()) },
                        imageUrl = imageUrl
                    )
                }
            }.map { it.await() } // Await all concurrent image fetches
            Outcome.Success(dogBreeds)
        } catch (e: Exception) {
            // Wrap any exception in an Outcome.Error for robust error handling
            Outcome.Error(e.message ?: "Unknown error")
        }
    }
}
