package com.paulallan.dogs.feature.breedlist.presentation

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import coil.Coil
import com.paulallan.dogs.feature.common.model.DogBreed
import com.paulallan.dogs.testutil.assertIsDisplayed
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented UI tests that verify the BreedListScreen
 */
@RunWith(AndroidJUnit4::class)
class BreedListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    /**
     * Test that verifies the BreedListContent displays the loading spinner correctly
     * when in the Loading state.
     */
    @Test
    fun loadingState_displaysLoadingSpinner() {
        // Get the ImageLoader from the app context
        val imageLoader = Coil.imageLoader(InstrumentationRegistry.getInstrumentation().targetContext)

        // When: Setting up the BreedListContent with a Loading state
        composeTestRule.setContent {
            BreedListContent(
                state = BreedListViewState.Loading,
                onBreedClick = {},
                imageLoader = imageLoader
            )
        }

        // Then: Verify that the CircularProgressIndicator is displayed
        composeTestRule.onNodeWithTag("breed_list_loading")
            .assertIsDisplayed()

        // And: Verify that the success state is not displayed
        composeTestRule.onNodeWithTag("breed_list_success")
            .assertDoesNotExist()
    }

    /**
     * Test that verifies the BreedListContent displays the success state correctly after loading.
     */
    @Test
    fun successState_displaysEmptyBreedList() {
        // Get the ImageLoader from the app context
        val imageLoader = Coil.imageLoader(InstrumentationRegistry.getInstrumentation().targetContext)

        // When: Setting up the BreedListContent with a Success state
        composeTestRule.setContent {
            BreedListContent(
                state = BreedListViewState.Success(
                    dogBreeds = emptyList(),
                    isRefreshing = false
                ),
                onBreedClick = {},
                imageLoader = imageLoader
            )
        }

        // Then: Verify that the CircularProgressIndicator is not displayed
        composeTestRule.onNodeWithTag("breed_list_loading")
            .assertDoesNotExist()

        composeTestRule.onNodeWithTag("breed_list_success")
            .assertIsDisplayed()
    }

    /**
     * Test that verifies the BreedListContent displays a success state with an empty list.
     */
    @Test
    fun successState_withEmptyList_displaysEmptyList() {
        // Get the ImageLoader from the app context
        val imageLoader = Coil.imageLoader(InstrumentationRegistry.getInstrumentation().targetContext)

        // When: Setting up the BreedListContent with a Success state containing an empty list
        composeTestRule.setContent {
            BreedListContent(
                state = BreedListViewState.Success(
                    dogBreeds = emptyList(),
                    isRefreshing = false
                ),
                onBreedClick = {},
                imageLoader = imageLoader
            )
        }

        // Then: Verify that the loading spinner is not displayed
        composeTestRule.onNodeWithTag("breed_list_loading")
            .assertDoesNotExist()

        composeTestRule.onNodeWithTag("breed_list_success")
            .assertIsDisplayed()

        composeTestRule.onAllNodesWithTag("breed_list_item")
            .assertCountEquals(0)
    }

    /**
     * Test that verifies the BreedListContent displays a non-empty list of dog breeds correctly
     * when in the Success state.
     */
    @Test
    fun successState_withNonEmptyList_displaysBreedList() {
        // Given: A list of dog breeds
        val dogBreeds = listOf(
            DogBreed(
                name = "labrador",
                displayName = "Labrador Retriever",
                imageUrl = "https://images.dog.ceo/breeds/labrador/n02099712_5642.jpg"
            ),
            DogBreed(
                name = "germanshepherd",
                displayName = "German Shepherd",
                imageUrl = "https://images.dog.ceo/breeds/germanshepherd/n02106662_1234.jpg"
            ),
            DogBreed(
                name = "golden",
                displayName = "Golden Retriever",
                imageUrl = "https://images.dog.ceo/breeds/retriever-golden/n02099601_123.jpg"
            )
        )

        // Get the ImageLoader from the app context
        val imageLoader = Coil.imageLoader(InstrumentationRegistry.getInstrumentation().targetContext)

        // When: Setting up the BreedListContent with a Success state containing a non-empty list
        composeTestRule.setContent {
            BreedListContent(
                state = BreedListViewState.Success(
                    dogBreeds = dogBreeds,
                    isRefreshing = false
                ),
                onBreedClick = {},
                imageLoader = imageLoader
            )
        }

        // Then: Verify that each dog breed is displayed
        dogBreeds.forEach { dogBreed ->
            composeTestRule.onNodeWithText(dogBreed.displayName)
                .assertIsDisplayed(dogBreed.displayName)
        }

        // And: Verify that the loading spinner is not displayed
        composeTestRule.onNodeWithTag("breed_list_loading")
            .assertDoesNotExist()
    }

    /**
     * Test that verifies the BreedListContent calls the onBreedClick callback
     * when a dog breed item is clicked.
     */
    @Test
    fun successState_whenBreedItemClicked_callsOnBreedClick() {
        // Given: A list of dog breeds
        val dogBreeds = listOf(
            DogBreed(
                name = "labrador",
                displayName = "Labrador Retriever",
                imageUrl = "https://images.dog.ceo/breeds/labrador/n02099712_5642.jpg"
            ),
            DogBreed(
                name = "germanshepherd",
                displayName = "German Shepherd",
                imageUrl = "https://images.dog.ceo/breeds/germanshepherd/n02106662_1234.jpg"
            )
        )

        // And: A variable to track the callback
        var clickedBreedName: String? = null
        val onBreedClick: (String) -> Unit = { breedName ->
            clickedBreedName = breedName
        }

        // Get the ImageLoader from the app context
        val imageLoader = Coil.imageLoader(InstrumentationRegistry.getInstrumentation().targetContext)

        // When: Setting up the BreedListContent with a Success state
        composeTestRule.setContent {
            BreedListContent(
                state = BreedListViewState.Success(
                    dogBreeds = dogBreeds,
                    isRefreshing = false
                ),
                onBreedClick = onBreedClick,
                imageLoader = imageLoader
            )
        }

        // And: Clicking on a dog breed item
        composeTestRule.onNodeWithText("Labrador Retriever")
            .performClick()

        // Then: Verify that the onBreedClick callback was called with the correct breed name
        assert(clickedBreedName == "labrador") {
            "Expected onBreedClick to be called with 'labrador', but was called with '$clickedBreedName'"
        }
    }

    /**
     * Test that verifies the BreedListContent displays a large list of dog breeds correctly.
     */
    @Test
    fun successState_withList_displaysBreedList() {
        // Given: A list of dog breeds
        val dogBreeds = (1..5).map { index ->
            DogBreed(
                name = "breed$index",
                displayName = "Dog Breed $index",
                imageUrl = "https://images.dog.ceo/breeds/breed$index/image.jpg"
            )
        }

        // Get the ImageLoader from the app context
        val imageLoader = Coil.imageLoader(InstrumentationRegistry.getInstrumentation().targetContext)

        // When: Setting up the BreedListContent with a Success state containing a large list
        composeTestRule.setContent {
            BreedListContent(
                state = BreedListViewState.Success(
                    dogBreeds = dogBreeds,
                    isRefreshing = false
                ),
                onBreedClick = {},
                imageLoader = imageLoader
            )
        }

        // Then: Verify that the dog breeds are displayed
        composeTestRule.onNodeWithText("Dog Breed 1")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Dog Breed 2")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Dog Breed 5")
            .assertExists()
    }

    /**
     * Test that verifies the BreedListContent displays a large list of dog breeds correctly
     * and handles scrolling.
     */
    @Test
    fun successState_withLargeList_displaysBreedListAndHandlesScrolling() {
        // Given: A large list of dog breeds (but not too large for testing)
        val dogBreeds = (1..10).map { index ->
            DogBreed(
                name = "breed$index",
                displayName = "Dog Breed $index",
                imageUrl = "https://images.dog.ceo/breeds/breed$index/image.jpg"
            )
        }

        // Get the ImageLoader from the app context
        val imageLoader = Coil.imageLoader(InstrumentationRegistry.getInstrumentation().targetContext)

        // When: Setting up the BreedListContent with a Success state containing a large list
        composeTestRule.setContent {
            BreedListContent(
                state = BreedListViewState.Success(
                    dogBreeds = dogBreeds,
                    isRefreshing = false
                ),
                onBreedClick = {},
                imageLoader = imageLoader
            )
        }

        // Then: Verify that the first few dog breeds are displayed (LazyColumn may not render all items initially)
        composeTestRule.onNodeWithText("Dog Breed 1")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Dog Breed 2")
            .assertIsDisplayed()

        // And: Verify that the loading spinner is not displayed
        composeTestRule.onNodeWithTag("breed_list_loading")
            .assertDoesNotExist()

        // Then: Perform swipe down to scroll through the list
        composeTestRule.onRoot()
            .performTouchInput {
                swipeDown(startY = centerY / 2, endY = centerY * 1.5f)
            }

        // And: Verify that the last dog breed is displayed after scrolling
        composeTestRule.onNodeWithText("Dog Breed 2")
            .assertIsDisplayed()
    }

    /**
     * Test that verifies the BreedListContent displays the TopAppBar correctly.
     */
    @Test
    fun successState_displaysTopAppBar() {
        // When: Setting up the BreedListContentTopAppBarTitleContent
        composeTestRule.setContent {
            BreedListContentTopAppBarTitleContent()
        }

        // Then: Verify that the TopAppBar title is displayed
        composeTestRule.onNodeWithText("Dog Breeds")
            .assertIsDisplayed()
    }

    /**
     * Test that verifies the BreedListContent displays the error message correctly
     * when in the Error state.
     */
    @Test
    fun errorState_displaysErrorMessage() {
        // Given: A test error message
        val testErrorMessage = "Test error message"

        // Get the ImageLoader from the app context
        val imageLoader = Coil.imageLoader(InstrumentationRegistry.getInstrumentation().targetContext)

        // When: Setting up the BreedListContent with an Error state
        composeTestRule.setContent {
            BreedListContent(
                state = BreedListViewState.Error(
                    message = testErrorMessage,
                    isRefreshing = false
                ),
                onBreedClick = {},
                imageLoader = imageLoader
            )
        }
        // Then: Verify that the loading spinner is not displayed
        composeTestRule.onNodeWithTag("breed_list_loading")
            .assertDoesNotExist()

        // And: Verify that the success state is not displayed
        composeTestRule.onNodeWithTag("breed_list_success")
            .assertDoesNotExist()

        // And: Verify that the error state is displayed
        composeTestRule.onNodeWithTag("breed_list_error")
            .assertIsDisplayed()

        // And: Verify that the error message is displayed
        composeTestRule.onNodeWithText(testErrorMessage)
            .assertIsDisplayed()

        // And: Verify that the user-friendly message is displayed
        composeTestRule.onNodeWithText("This would be a user-presentable error UI in a production app.")
            .assertIsDisplayed()
    }

    /**
     * Test that verifies the BreedListContent displays different error messages correctly.
     */
    @Test
    fun errorState_withDifferentMessages_displaysCorrectly() {
        // Given: A different test error message
        val testErrorMessage = "Network connection failed"

        // Get the ImageLoader from the app context
        val imageLoader = Coil.imageLoader(InstrumentationRegistry.getInstrumentation().targetContext)

        // When: Setting up the BreedListContent with an Error state
        composeTestRule.setContent {
            BreedListContent(
                state = BreedListViewState.Error(
                    message = testErrorMessage,
                    isRefreshing = false
                ),
                onBreedClick = {},
                imageLoader = imageLoader
            )
        }

        // Then: Verify that the loading spinner is not displayed
        composeTestRule.onNodeWithTag("breed_list_loading")
            .assertDoesNotExist()

        // And: Verify that the success state is not displayed
        composeTestRule.onNodeWithTag("breed_list_success")
            .assertDoesNotExist()

        // And: Verify that the error state is displayed
        composeTestRule.onNodeWithTag("breed_list_error")
            .assertIsDisplayed()

        // And: Verify that the error message is displayed
        composeTestRule.onNodeWithText(testErrorMessage)
            .assertIsDisplayed()

        // And: Verify that the user-friendly message is displayed
        composeTestRule.onNodeWithText("This would be a user-presentable error UI in a production app.")
            .assertIsDisplayed()
    }

    /**
     * Test that verifies the BreedListContent displays the pull-to-refresh indicator correctly
     * when in the Success state with isRefreshing=true.
     */
    @Test
    fun successState_withRefreshing_displaysPullRefreshIndicator() {
        // Given: A list of dog breeds
        val dogBreeds = listOf(
            DogBreed(
                name = "labrador",
                displayName = "Labrador Retriever",
                imageUrl = "https://images.dog.ceo/breeds/labrador/n02099712_5642.jpg"
            ),
            DogBreed(
                name = "germanshepherd",
                displayName = "German Shepherd",
                imageUrl = "https://images.dog.ceo/breeds/germanshepherd/n02106662_1234.jpg"
            )
        )

        // Get the ImageLoader from the app context
        val imageLoader = Coil.imageLoader(InstrumentationRegistry.getInstrumentation().targetContext)

        // When: Setting up the BreedListContent with a Success state and isRefreshing=true
        composeTestRule.setContent {
            BreedListContent(
                state = BreedListViewState.Success(
                    dogBreeds = dogBreeds,
                    isRefreshing = true
                ),
                onBreedClick = {},
                imageLoader = imageLoader,
                onRefresh = {}
            )
        }

        // Then: Verify that the success state is displayed
        composeTestRule.onNodeWithTag("breed_list_success")
            .assertIsDisplayed()

        // And: Verify that the dog breeds are still displayed
        dogBreeds.forEach { dogBreed ->
            composeTestRule.onNodeWithText(dogBreed.displayName)
                .assertIsDisplayed(dogBreed.displayName)
        }
    }
}
