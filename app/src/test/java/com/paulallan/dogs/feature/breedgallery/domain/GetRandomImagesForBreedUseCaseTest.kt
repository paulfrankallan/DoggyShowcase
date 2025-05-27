package com.paulallan.dogs.feature.breedgallery.domain

import com.paulallan.dogs.core.network.ApiConstants
import com.paulallan.dogs.core.network.DogCeoApi
import com.paulallan.dogs.core.outcome.Outcome
import com.paulallan.dogs.feature.breedgallery.data.network.model.BreedImagesResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetRandomImagesForBreedUseCaseTest {

    private lateinit var dogCeoApi: DogCeoApi
    private lateinit var getRandomImagesForBreedUseCase: GetRandomImagesForBreedUseCase

    @Before
    fun setup() {
        dogCeoApi = mockk()
        getRandomImagesForBreedUseCase = GetRandomImagesForBreedUseCase(dogCeoApi)
    }

    /**
     * Verifies that a successful API call returns a success outcome with the correct image URLs.
     */
    @Test
    fun `invoke returns success with list of image URLs when API call is successful`() = runTest {
        // Given
        val breed = "hound"
        val count = 5
        val imageUrls = listOf(
            "https://images.dog.ceo/breeds/hound/hound1.jpg",
            "https://images.dog.ceo/breeds/hound/hound2.jpg",
            "https://images.dog.ceo/breeds/hound/hound3.jpg",
            "https://images.dog.ceo/breeds/hound/hound4.jpg",
            "https://images.dog.ceo/breeds/hound/hound5.jpg"
        )
        val breedImagesResponse = BreedImagesResponse(
            message = imageUrls,
            status = "success"
        )

        coEvery { dogCeoApi.getRandomImagesForBreed(breed, count) } returns breedImagesResponse

        // When
        val outcome = getRandomImagesForBreedUseCase(breed, count)

        // Then
        assertTrue(outcome is Outcome.Success)
        val outcomeImageUrls = (outcome as Outcome.Success).data
        assertEquals(imageUrls, outcomeImageUrls)
        coVerify { dogCeoApi.getRandomImagesForBreed(breed, count) }
    }

    /**
     * Verifies that the default count is used when no count is provided to the use case.
     */
    @Test
    fun `invoke uses default count parameter when count is not provided`() = runTest {
        // Given
        val breed = "hound"
        val defaultCount = ApiConstants.DEFAULT_RANDOM_DOG_IMAGE_COUNT
        val imageUrls = List(defaultCount) { index ->
            "https://images.dog.ceo/breeds/hound/hound${index + 1}.jpg"
        }
        val breedImagesResponse = BreedImagesResponse(
            message = imageUrls,
            status = "success"
        )

        coEvery { dogCeoApi.getRandomImagesForBreed(breed, defaultCount) } returns breedImagesResponse

        // When
        val outcome = getRandomImagesForBreedUseCase(breed)

        // Then
        assertTrue(outcome is Outcome.Success)
        val outcomeImageUrls = (outcome as Outcome.Success).data
        assertEquals(imageUrls, outcomeImageUrls)
        coVerify { dogCeoApi.getRandomImagesForBreed(breed, defaultCount) }
    }

    /**
     * Verifies that an error outcome is returned when the API status is 'error'.
     */
    @Test
    fun `invoke returns error when API returns error status`() = runTest {
        // Given
        val breed = "hound"
        val count = 5
        val breedImagesResponse = BreedImagesResponse(
            message = emptyList(),
            status = "error"
        )

        coEvery { dogCeoApi.getRandomImagesForBreed(breed, count) } returns breedImagesResponse

        // When
        val outcome = getRandomImagesForBreedUseCase(breed, count)

        // Then
        assertTrue(outcome is Outcome.Error)
        assertEquals("API error status: error", (outcome as Outcome.Error).message)
    }

    /**
     * Verifies that an error outcome is returned for any non-success API status.
     */
    @Test
    fun `invoke returns error when API returns arbitrary non success status`() = runTest {
        // Given
        val breed = "hound"
        val count = 5
        val breedImagesResponse = BreedImagesResponse(
            message = emptyList(),
            status = "oops" // Simulating an arbitrary non-success status
        )

        coEvery { dogCeoApi.getRandomImagesForBreed(breed, count) } returns breedImagesResponse

        // When
        val outcome = getRandomImagesForBreedUseCase(breed, count)

        // Then
        assertTrue(outcome is Outcome.Error)
        assertEquals("API error status: oops", (outcome as Outcome.Error).message)
    }

    /**
     * Verifies that an error outcome is returned when an exception is thrown by the API call.
     */
    @Test
    fun `invoke returns error when an exception is thrown`() = runTest {
        // Given
        val breed = "hound"
        val count = 5
        val errorMessage = "Network error"

        coEvery { dogCeoApi.getRandomImagesForBreed(breed, count) } throws RuntimeException(errorMessage)

        // When
        val outcome = getRandomImagesForBreedUseCase(breed, count)

        // Then
        assertTrue(outcome is Outcome.Error)
        assertEquals(errorMessage, (outcome as Outcome.Error).message)
    }
}
