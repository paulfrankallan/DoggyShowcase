package com.paulallan.dogs.core.network

import com.paulallan.dogs.feature.breedlist.data.network.model.BreedImageResponse
import com.paulallan.dogs.feature.breedlist.data.network.model.BreedListResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface DogCeoApi {
    @GET(ApiConstants.ENDPOINT_ALL_BREEDS)
    suspend fun getAllBreeds(): BreedListResponse

    @GET(ApiConstants.ENDPOINT_RANDOM_IMAGE_FOR_BREED)
    suspend fun getRandomImageForBreed(
        @Path("breed") breed: String
    ): BreedImageResponse
}