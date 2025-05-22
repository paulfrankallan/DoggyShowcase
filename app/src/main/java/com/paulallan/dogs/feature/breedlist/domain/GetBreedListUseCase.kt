package com.paulallan.dogs.feature.breedlist.domain

import com.paulallan.dogs.core.network.DogCeoApi
import com.paulallan.dogs.core.network.requireSuccess
import com.paulallan.dogs.core.result.Result
import com.paulallan.dogs.feature.common.model.DogBreed
import kotlinx.coroutines.coroutineScope

class GetBreedListUseCase(private val api: DogCeoApi) {
    suspend operator fun invoke(): Result<List<DogBreed>> = coroutineScope {
        try {
            // Fetch all breeds and ensure the response is successful, then extract breed names
            val breedListResponse = api.getAllBreeds()
            val breedNamesList = breedListResponse.requireSuccess(breedListResponse.message).keys.toList()
            Result.Success(breedNamesList.map { DogBreed(name = it, imageUrl = "") })
        } catch (e: Exception) {
            // Wrap any exception in a Result.Error for robust error handling
            Result.Error(e.message ?: "Unknown error")
        }
    }
}