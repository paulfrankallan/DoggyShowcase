package com.paulallan.dogs.core.network

object ApiConstants {
    const val BASE_URL = "https://dog.ceo/api/"
    const val DEFAULT_RANDOM_DOG_IMAGE_COUNT = 10

    // Endpoints
    const val ENDPOINT_ALL_BREEDS = "breeds/list/all"
    const val ENDPOINT_RANDOM_IMAGE_FOR_BREED = "breed/{breed}/images/random"
    const val ENDPOINT_RANDOM_IMAGES_FOR_BREED = "breed/{breed}/images/random/{count}"
}