package com.paulallan.dogs.feature.breedlist.presentation

import com.paulallan.dogs.core.outcome.Outcome
import com.paulallan.dogs.feature.breedlist.domain.GetBreedListUseCase
import com.paulallan.dogs.feature.common.model.DogBreed
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class BreedListViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var getBreedListUseCase: GetBreedListUseCase
    private lateinit var viewModel: BreedListViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getBreedListUseCase = mockk()
        viewModel = BreedListViewModel(getBreedListUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is Loading`() {
        // Then
        assertEquals(BreedListViewState.Loading, viewModel.state.value)
    }

    @Test
    fun `LoadDogs intent calls getBreedListUseCase`() = runTest {
        // Given
        val dogBreeds = listOf(
            DogBreed(
                name = "hound",
                displayName = "Hound",
                imageUrl = "https://images.dog.ceo/breeds/hound/hound1.jpg"
            )
        )
        coEvery { getBreedListUseCase() } returns Outcome.Success(dogBreeds)

        // When
        viewModel.handleIntent(BreedListIntent.LoadBreeds)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { getBreedListUseCase() }
    }

    @Test
    fun `initialDataLoad calls LoadDogs intent`() = runTest {
        // Given
        val dogBreeds = listOf(
            DogBreed(
                name = "hound",
                displayName = "Hound",
                imageUrl = "https://images.dog.ceo/breeds/hound/hound1.jpg"
            )
        )
        coEvery { getBreedListUseCase() } returns Outcome.Success(dogBreeds)

        // When
        viewModel.initialDataLoad()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { getBreedListUseCase() }
    }

    @Test
    fun `LoadDogs intent with error outcome calls getBreedListUseCase`() = runTest {
        // Given
        val errorMessage = "Failed to load breeds"
        coEvery { getBreedListUseCase() } returns Outcome.Error(errorMessage)

        // When
        viewModel.handleIntent(BreedListIntent.LoadBreeds)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { getBreedListUseCase() }
    }

    @Test
    fun `LoadDogs intent with exception calls getBreedListUseCase`() = runTest {
        // Given
        val exceptionMessage = "Network error"
        coEvery { getBreedListUseCase() } throws RuntimeException(exceptionMessage)

        // When
        viewModel.handleIntent(BreedListIntent.LoadBreeds)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { getBreedListUseCase() }
    }
}
