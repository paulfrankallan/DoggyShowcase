package com.paulallan.dogs.feature.breedlist.domain

import com.paulallan.dogs.core.network.DogCeoApi
import com.paulallan.dogs.core.outcome.Outcome
import com.paulallan.dogs.feature.breedlist.data.network.model.BreedImageResponse
import com.paulallan.dogs.feature.breedlist.data.network.model.BreedListResponse
import com.paulallan.dogs.feature.common.model.DogBreed
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
class GetBreedListUseCaseTest {

    private lateinit var dogCeoApi: DogCeoApi
    private lateinit var getBreedListUseCase: GetBreedListUseCase

    @Before
    fun setup() {
        dogCeoApi = mockk()
        getBreedListUseCase = GetBreedListUseCase(dogCeoApi)
    }

    @Test
    fun `invoke returns success with list of dog breeds when API calls are successful`() = runTest {
        // Given
        val breedsMap = mapOf(
            "hound" to listOf("afghan", "basset"),
            "bulldog" to listOf("boston", "french")
        )
        val breedListResponse = BreedListResponse(
            message = breedsMap,
            status = "success"
        )

        // Mock responses for each breed and sub-breed
        val imageUrls = mapOf(
            "hound" to "https://images.dog.ceo/breeds/hound/hound1.jpg",
            "hound/afghan" to "https://images.dog.ceo/breeds/hound-afghan/afghan1.jpg",
            "hound/basset" to "https://images.dog.ceo/breeds/hound-basset/basset1.jpg",
            "bulldog" to "https://images.dog.ceo/breeds/bulldog/bulldog1.jpg",
            "bulldog/boston" to "https://images.dog.ceo/breeds/bulldog-boston/boston1.jpg",
            "bulldog/french" to "https://images.dog.ceo/breeds/bulldog-french/french1.jpg"
        )

        coEvery { dogCeoApi.getAllBreeds() } returns breedListResponse

        imageUrls.forEach { (breed, imageUrl) ->
            coEvery { dogCeoApi.getRandomImageForBreed(breed) } returns BreedImageResponse(
                message = imageUrl,
                status = "success"
            )
        }

        // When
        val outcome = getBreedListUseCase()

        // Then
        assertTrue(outcome is Outcome.Success)
        val dogBreeds = (outcome as Outcome.Success).data
        assertEquals(6, dogBreeds.size)

        // Verify all expected breeds are present with correct data
        val expectedBreeds = listOf(
            DogBreed(
                name = "hound",
                displayName = "Hound",
                imageUrl = "https://images.dog.ceo/breeds/hound/hound1.jpg"
            ),
            DogBreed(
                name = "hound/afghan",
                displayName = "Hound/afghan",
                imageUrl = "https://images.dog.ceo/breeds/hound-afghan/afghan1.jpg"
            ),
            DogBreed(
                name = "hound/basset",
                displayName = "Hound/basset",
                imageUrl = "https://images.dog.ceo/breeds/hound-basset/basset1.jpg"
            ),
            DogBreed(
                name = "bulldog",
                displayName = "Bulldog",
                imageUrl = "https://images.dog.ceo/breeds/bulldog/bulldog1.jpg"
            ),
            DogBreed(
                name = "bulldog/boston",
                displayName = "Bulldog/boston",
                imageUrl = "https://images.dog.ceo/breeds/bulldog-boston/boston1.jpg"
            ),
            DogBreed(
                name = "bulldog/french",
                displayName = "Bulldog/french",
                imageUrl = "https://images.dog.ceo/breeds/bulldog-french/french1.jpg"
            )
        )

        // Verify each expected breed is in the result
        expectedBreeds.forEach { expectedBreed ->
            assertTrue(dogBreeds.any { it.name == expectedBreed.name && it.imageUrl == expectedBreed.imageUrl })
        }

        // Verify API calls
        coVerify { dogCeoApi.getAllBreeds() }
        imageUrls.keys.forEach { breed ->
            coVerify { dogCeoApi.getRandomImageForBreed(breed) }
        }
    }

    @Test
    fun `invoke returns error when getAllBreeds API returns error status`() = runTest {
        // Given
        val breedListResponse = BreedListResponse(
            message = emptyMap(),
            status = "error"
        )

        coEvery { dogCeoApi.getAllBreeds() } returns breedListResponse

        // When
        val outcome = getBreedListUseCase()

        // Then
        assertTrue(outcome is Outcome.Error)
        assertEquals("API error status: error", (outcome as Outcome.Error).message)
        coVerify { dogCeoApi.getAllBreeds() }
    }


    @Test
    fun `invoke returns error when an exception is thrown during getAllBreeds`() = runTest {
        // Given
        val errorMessage = "Network error"

        coEvery { dogCeoApi.getAllBreeds() } throws RuntimeException(errorMessage)

        // When
        val outcome = getBreedListUseCase()

        // Then
        assertTrue(outcome is Outcome.Error)
        assertEquals(errorMessage, (outcome as Outcome.Error).message)
        coVerify { dogCeoApi.getAllBreeds() }
    }
}
