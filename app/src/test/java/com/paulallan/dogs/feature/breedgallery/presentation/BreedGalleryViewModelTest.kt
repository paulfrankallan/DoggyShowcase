package com.paulallan.dogs.feature.breedgallery.presentation

import com.paulallan.dogs.core.network.ApiConstants
import com.paulallan.dogs.core.outcome.Outcome
import com.paulallan.dogs.feature.breedgallery.domain.GetRandomImagesForBreedUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class BreedGalleryViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var getRandomImagesForBreedUseCase: GetRandomImagesForBreedUseCase
    private lateinit var viewModel: BreedGalleryViewModel
    private val testBreed = "hound"

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getRandomImagesForBreedUseCase = mockk()
        viewModel = BreedGalleryViewModel(getRandomImagesForBreedUseCase, testBreed, testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * Verifies that the initial state of the ViewModel is Loading.
     */
    @Test
    fun `initial state is Loading`() {
        // Then
        assertEquals(BreedGalleryViewState.Loading, viewModel.state.value)
    }

    /**
     * Verifies that LoadGallery intent calls getRandomImagesForBreedUseCase with the correct parameters.
     */
    @Test
    fun `LoadGallery intent calls getRandomImagesForBreedUseCase`() = runTest {
        // Given
        val imageUrls = listOf(
            "https://images.dog.ceo/breeds/hound/hound1.jpg",
            "https://images.dog.ceo/breeds/hound/hound2.jpg"
        )
        coEvery { 
            getRandomImagesForBreedUseCase(
                breed = testBreed,
                count = ApiConstants.DEFAULT_RANDOM_DOG_IMAGE_COUNT
            ) 
        } returns Outcome.Success(imageUrls)

        // When
        viewModel.handleIntent(BreedGalleryIntent.LoadGallery(testBreed))
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { 
            getRandomImagesForBreedUseCase(
                breed = testBreed,
                count = ApiConstants.DEFAULT_RANDOM_DOG_IMAGE_COUNT
            ) 
        }
    }

    /**
     * Verifies that RefreshGallery intent calls getRandomImagesForBreedUseCase with the correct parameters.
     */
    @Test
    fun `RefreshGallery intent calls getRandomImagesForBreedUseCase`() = runTest {
        // Given
        val imageUrls = listOf(
            "https://images.dog.ceo/breeds/hound/hound1.jpg",
            "https://images.dog.ceo/breeds/hound/hound2.jpg"
        )
        coEvery { 
            getRandomImagesForBreedUseCase(
                breed = testBreed,
                count = ApiConstants.DEFAULT_RANDOM_DOG_IMAGE_COUNT
            ) 
        } returns Outcome.Success(imageUrls)

        // When
        viewModel.handleIntent(BreedGalleryIntent.RefreshGallery(testBreed))
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { 
            getRandomImagesForBreedUseCase(
                breed = testBreed,
                count = ApiConstants.DEFAULT_RANDOM_DOG_IMAGE_COUNT
            ) 
        }
    }

    /**
     * Verifies that the ViewModel loads images when state is collected and updates state correctly.
     */
    @Test
    fun `ViewModel loads images when state is collected and updates state correctly`() = runTest {
        // Given
        val imageUrls = listOf(
            "https://images.dog.ceo/breeds/hound/hound1.jpg",
            "https://images.dog.ceo/breeds/hound/hound2.jpg"
        )

        coEvery { 
            getRandomImagesForBreedUseCase(
                breed = any(),
                count = any()
            ) 
        } returns Outcome.Success(imageUrls)

        // Create a new ViewModel
        val testViewModel = BreedGalleryViewModel(getRandomImagesForBreedUseCase, testBreed, testDispatcher)

        // Collect states
        val states = mutableListOf<BreedGalleryViewState>()
        val job = launch {
            testViewModel.state.toList(states)
        }

        // Advance time to complete initialDataLoad
        testDispatcher.scheduler.advanceUntilIdle()

        // Verify states
        assertEquals(2, states.size)
        assertEquals(BreedGalleryViewState.Loading, states[0])
        assertEquals(
            BreedGalleryViewState.Success(
                breed = testBreed,
                imageUrls = imageUrls,
                isRefreshing = false
            ), 
            states[1]
        )

        // Verify use case was called
        coVerify { 
            getRandomImagesForBreedUseCase(
                breed = testBreed,
                count = ApiConstants.DEFAULT_RANDOM_DOG_IMAGE_COUNT
            ) 
        }

        job.cancel()
    }

    /**
     * Verifies that LoadGallery intent updates state to Success with correct data.
     */
    @Test
    fun `LoadGallery intent updates state correctly`() = runTest {
        // Given
        val imageUrls = listOf(
            "https://images.dog.ceo/breeds/hound/hound1.jpg",
            "https://images.dog.ceo/breeds/hound/hound2.jpg"
        )

        coEvery { 
            getRandomImagesForBreedUseCase(
                breed = any(),
                count = any()
            ) 
        } returns Outcome.Success(imageUrls)

        // Create a new ViewModel
        val testViewModel = BreedGalleryViewModel(getRandomImagesForBreedUseCase, testBreed, testDispatcher)

        // Collect states
        val states = mutableListOf<BreedGalleryViewState>()
        val job = launch {
            testViewModel.state.toList(states)
        }

        // Advance time to complete initialDataLoad
        testDispatcher.scheduler.advanceUntilIdle()

        // When - send LoadGallery intent
        testViewModel.handleIntent(BreedGalleryIntent.LoadGallery(testBreed))
        testDispatcher.scheduler.advanceUntilIdle()

        // Then - verify states
        // We expect at least 2 states: Loading and Success
        assertEquals(2, states.size)
        assertEquals(BreedGalleryViewState.Loading, states[0])

        // Verify the final state is Success with the correct data
        val finalState = states.last()
        assertEquals(
            BreedGalleryViewState.Success(
                breed = testBreed,
                imageUrls = imageUrls,
                isRefreshing = false
            ), 
            finalState
        )

        // Verify the use case was called
        coVerify { 
            getRandomImagesForBreedUseCase(
                breed = testBreed,
                count = ApiConstants.DEFAULT_RANDOM_DOG_IMAGE_COUNT
            ) 
        }

        job.cancel()
    }
}
