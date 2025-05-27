package com.paulallan.dogs.feature.breedgallery.domain

import com.paulallan.dogs.core.network.ApiConstants
import com.paulallan.dogs.core.network.DogCeoApi
import com.paulallan.dogs.core.network.requireSuccess
import com.paulallan.dogs.core.outcome.Outcome
import kotlinx.coroutines.coroutineScope

/**
 * Use case for fetching a list of random images for a specific dog breed from the Dog CEO API.
 *
 * @property api The Dog CEO API client used to fetch images.
 */
class GetRandomImagesForBreedUseCase(private val api: DogCeoApi) {

    /**
     * Invokes the use case to fetch random images for the given breed.
     *
     * @param breed The breed name (may include sub-breed, e.g., "bulldog/french").
     * @param count The number of random images to fetch. Defaults to [ApiConstants.DEFAULT_RANDOM_DOG_IMAGE_COUNT].
     * @return [Outcome] containing a list of image URLs on success, or an error message on failure.
     */
    suspend operator fun invoke(
        breed: String,
        count: Int = ApiConstants.DEFAULT_RANDOM_DOG_IMAGE_COUNT
    ): Outcome<List<String>> =
        coroutineScope {
            try {
                // Fetch random images for the breed and ensure the response is successful
                val response = api.getRandomImagesForBreed(breed, count)
                val images = response.requireSuccess(response.message)
                Outcome.Success(images)
            } catch (e: Exception) {
                // Wrap any exception in a Outcome.Error for robust error handling
                Outcome.Error(e.message ?: "Unknown error")
            }
        }
}
