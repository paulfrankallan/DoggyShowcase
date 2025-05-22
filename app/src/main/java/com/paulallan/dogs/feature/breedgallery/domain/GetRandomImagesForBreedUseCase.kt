package com.paulallan.dogs.feature.breedgallery.domain

import com.paulallan.dogs.core.network.ApiConstants
import com.paulallan.dogs.core.network.DogCeoApi
import com.paulallan.dogs.core.network.requireSuccess
import com.paulallan.dogs.core.result.Result
import kotlinx.coroutines.coroutineScope

class GetRandomImagesForBreedUseCase(private val api: DogCeoApi) {
    suspend operator fun invoke(
        breed: String,
        count: Int = ApiConstants.DEFAULT_RANDOM_DOG_IMAGE_COUNT
    ): Result<List<String>> =
        coroutineScope {
            try {
                // Fetch random images for the breed and ensure the response is successful
                val response = api.getRandomImagesForBreed(breed, count)
                val images = response.requireSuccess(response.message)
                Result.Success(images)
            } catch (e: Exception) {
                // Wrap any exception in a Result.Error for robust error handling
                Result.Error(e.message ?: "Unknown error")
            }
        }
}
