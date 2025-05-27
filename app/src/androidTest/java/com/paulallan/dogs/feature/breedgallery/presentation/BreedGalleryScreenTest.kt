package com.paulallan.dogs.feature.breedgallery.presentation

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented tests that verify the BreedGalleryScreen
 */
@RunWith(AndroidJUnit4::class)
class BreedGalleryScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    /**
     * Test that verifies the BreedGalleryContent displays a loading state correctly.
     */
    @Test
    fun loadingState_displaysLoadingSpinner() {
        // When: Setting up the BreedGalleryContent with a Loading state
        composeTestRule.setContent {
            BreedGalleryContent(
                state = BreedGalleryViewState.Loading,
                onRefresh = {}
            )
        }

        // Then: Verify that the loading spinner is displayed
        composeTestRule.onNodeWithTag("loading_spinner")
            .assertIsDisplayed()

        // And: Verify that the gallery grid is not displayed
        composeTestRule.onNodeWithTag("gallery_grid")
            .assertDoesNotExist()

        // And: Verify that the success state is not displayed
        composeTestRule.onNodeWithTag("breed_gallery_success")
            .assertDoesNotExist()

        // And: Verify that the error state is not displayed
        composeTestRule.onNodeWithTag("breed_list_error")
            .assertDoesNotExist()
    }

    /**
     * Test that verifies the BreedGalleryContent displays a non-empty list of dog images correctly
     * when in the Success state.
     */
    @Test
    fun successState_withNonEmptyList_displaysGalleryGrid() {
        // Given: A list of image URLs
        val imageUrls = listOf(
            "https://images.dog.ceo/breeds/labrador/n02099712_5642.jpg",
            "https://images.dog.ceo/breeds/labrador/n02099712_3400.jpg",
            "https://images.dog.ceo/breeds/labrador/n02099712_1123.jpg",
            "https://images.dog.ceo/breeds/labrador/n02099712_1234.jpg"
        )

        // When: Setting up the BreedGalleryContent with a Success state containing a non-empty list
        composeTestRule.setContent {
            BreedGalleryContent(
                state = BreedGalleryViewState.Success(
                    breed = "labrador",
                    imageUrls = imageUrls,
                    isRefreshing = false
                ),
                onRefresh = {}
            )
        }

        // Then: Verify that the error state is not displayed
        composeTestRule.onNodeWithTag("breed_gallery_error")
            .assertDoesNotExist()

        // And: Verify that the success state is displayed
        composeTestRule.onNodeWithTag("breed_gallery_success")
            .assertIsDisplayed()

        // And: Verify that the loading spinner is not displayed
        composeTestRule.onNodeWithTag("breed_gallery_loading")
            .assertDoesNotExist()

        // And: Verify that the gallery grid is displayed
        composeTestRule.onNodeWithTag("gallery_grid")
            .assertIsDisplayed()

        // And: Verify that all image items are displayed
        imageUrls.forEachIndexed { index, _ ->
            composeTestRule.onNodeWithTag("gallery_item_$index")
                .assertIsDisplayed()
        }
    }

    /**
     * Test that verifies the BreedGalleryContent displays an error message correctly
     * when in the Error state.
     */
    @Test
    fun errorState_displaysErrorMessage() {
        // Given: An error message
        val errorMessage = "Failed to load images"

        // When: Setting up the BreedGalleryContent with an Error state
        composeTestRule.setContent {
            BreedGalleryContent(
                state = BreedGalleryViewState.Error(errorMessage),
                onRefresh = {}
            )
        }

        // Then: Verify that the error state is displayed
        composeTestRule.onNodeWithTag("breed_gallery_error")
            .assertIsDisplayed()

        // And: Verify that the error state is displayed
        composeTestRule.onNodeWithTag("breed_gallery_error")
            .assertIsDisplayed()

        // And: Verify that the error message is displayed
        composeTestRule.onNodeWithText(errorMessage)
            .assertIsDisplayed()

        // And: Verify that the loading spinner is not displayed
        composeTestRule.onNodeWithTag("loading_spinner")
            .assertDoesNotExist()
    }

    /**
     * Test that verifies the BreedGalleryScreen calls the refresh function when pull-to-refresh is triggered.
     */
    @Test
    fun successState_whenPullToRefresh_callsOnRefresh() {
        // Given: A list of image URLs
        val imageUrls = listOf(
            "https://images.dog.ceo/breeds/labrador/n02099712_5642.jpg",
            "https://images.dog.ceo/breeds/labrador/n02099712_3400.jpg"
        )

        // And: A variable to track the callback
        var refreshCalled = false
        val onRefresh: () -> Unit = {
            refreshCalled = true
        }

        // When: Setting up the BreedGalleryContent with a Success state
        composeTestRule.setContent {
            BreedGalleryContent(
                state = BreedGalleryViewState.Success(
                    breed = "labrador",
                    imageUrls = imageUrls,
                    isRefreshing = false
                ),
                onRefresh = onRefresh
            )
        }

        // And: Performing a pull-to-refresh gesture
        composeTestRule.onRoot()
            .performTouchInput { 
                swipeDown(startY = centerY / 2, endY = centerY * 1.5f) 
            }

        // Then: Verify that the onRefresh callback was called
        assert(refreshCalled) { "Expected onRefresh to be called, but it wasn't" }
    }

    /**
     * Test that verifies the BreedGalleryScreen displays the TopAppBar with the correct breed name.
     */
    @Test
    fun successState_displaysTopAppBarWithBreedName() {
        // Given: A breed name
        val breedName = "labrador"

        // When: Setting up the BreedGalleryTopAppBarTitleContent
        composeTestRule.setContent {
            BreedGalleryTopAppBarTitleContent(breed = breedName)
        }

        // Then: Verify that the TopAppBar title displays the capitalized breed name
        composeTestRule.onNodeWithText("Labrador")
            .assertIsDisplayed()
    }

    /**
     * Test that verifies the BreedGalleryContent displays a success state with a large list of images.
     */
    @Test
    fun successState_withFullGridOf10Images_displaysGalleryGrid() {
        // Given: A large list of image URLs (but not too large for testing)
        val imageUrls = (1..10).map { index ->
            "https://images.dog.ceo/breeds/labrador/n02099712_$index.jpg"
        }

        // When: Setting up the BreedGalleryContent with a Success state containing a large list
        composeTestRule.setContent {
            BreedGalleryContent(
                state = BreedGalleryViewState.Success(
                    breed = "labrador",
                    imageUrls = imageUrls,
                    isRefreshing = false
                ),
                onRefresh = {}
            )
        }

        // Then: Verify that the success state is displayed
        composeTestRule.onNodeWithTag("breed_gallery_success")
            .assertIsDisplayed()

        // And: Verify that the gallery grid is displayed
        composeTestRule.onNodeWithTag("gallery_grid")
            .assertIsDisplayed()

        // And: Verify that all image items are displayed
        imageUrls.forEachIndexed { index, _ ->
            composeTestRule.onNodeWithTag("gallery_item_$index")
                .assertIsDisplayed()
        }
    }

    /**
     * Test that verifies the BreedGalleryContent displays a success state with an odd number of images.
     * This test ensures that all items are displayed correctly even with an odd number of items.
     */
    @Test
    fun successState_withOddNumberOfItems_displaysAllItems() {
        // Given: A list with an odd number of image URLs
        val imageUrls = (1..5).map { index ->
            "https://images.dog.ceo/breeds/labrador/n02099712_$index.jpg"
        }

        // When: Setting up the BreedGalleryContent with a Success state containing an odd number of items
        composeTestRule.setContent {
            BreedGalleryContent(
                state = BreedGalleryViewState.Success(
                    breed = "labrador",
                    imageUrls = imageUrls,
                    isRefreshing = false
                ),
                onRefresh = {}
            )
        }

        // Then: Verify that the success state is displayed
        composeTestRule.onNodeWithTag("breed_gallery_success")
            .assertIsDisplayed()

        // And: Verify that the gallery grid is displayed
        composeTestRule.onNodeWithTag("gallery_grid")
            .assertIsDisplayed()

        // And: Verify that all image items are displayed
        imageUrls.forEachIndexed { index, _ ->
            composeTestRule.onNodeWithTag("gallery_item_$index")
                .assertIsDisplayed()
        }
    }
    /**
     * Test that verifies the BreedGalleryContent displays a gallery grid and responds to refresh
     * by showing a new set of images.
     */
    @Test
    fun successState_displaysGalleryGrid_andRespondsToRefresh() {
        // Given: Initial set of image URLs
        val initialImageUrls = listOf(
            "https://images.dog.ceo/breeds/labrador/n02099712_5642.jpg",
            "https://images.dog.ceo/breeds/labrador/n02099712_3400.jpg",
            "https://images.dog.ceo/breeds/labrador/n02099712_1123.jpg",
            "https://images.dog.ceo/breeds/labrador/n02099712_1234.jpg"
        )

        // And: A variable to track if refresh was called
        var refreshCalled = false
        val onRefresh: () -> Unit = {
            refreshCalled = true
        }

        // When: Setting up the BreedGalleryContent with initial Success state
        composeTestRule.setContent {
            BreedGalleryContent(
                state = BreedGalleryViewState.Success(
                    breed = "labrador",
                    imageUrls = initialImageUrls,
                    isRefreshing = false
                ),
                onRefresh = onRefresh
            )
        }

        // Then: Verify that the success state is displayed
        composeTestRule.onNodeWithTag("breed_gallery_success")
            .assertIsDisplayed()

        // And: Verify that the gallery grid is displayed
        composeTestRule.onNodeWithTag("gallery_grid")
            .assertIsDisplayed()

        // And: Verify that all initial image items are displayed
        initialImageUrls.forEachIndexed { index, _ ->
            composeTestRule.onNodeWithTag("gallery_item_$index")
                .assertIsDisplayed()
        }

        // When: Performing a pull-to-refresh gesture
        composeTestRule.onRoot()
            .performTouchInput { 
                swipeDown(startY = centerY / 2, endY = centerY * 1.5f) 
            }

        // Then: Verify that the onRefresh callback was called
        assert(refreshCalled) { "Expected onRefresh to be called, but it wasn't" }
    }

    /**
     * Test that verifies the BreedGalleryContent displays a refreshed set of images
     * after a refresh operation.
     */
    @Test
    fun successState_afterRefresh_displaysNewImages() {
        // Given: A set of image URLs after refresh
        val refreshedImageUrls = listOf(
            "https://images.dog.ceo/breeds/labrador/n02099712_9876.jpg",
            "https://images.dog.ceo/breeds/labrador/n02099712_5432.jpg",
            "https://images.dog.ceo/breeds/labrador/n02099712_2468.jpg",
            "https://images.dog.ceo/breeds/labrador/n02099712_1357.jpg"
        )

        // When: Setting up the BreedGalleryContent with refreshed Success state
        composeTestRule.setContent {
            BreedGalleryContent(
                state = BreedGalleryViewState.Success(
                    breed = "labrador",
                    imageUrls = refreshedImageUrls,
                    isRefreshing = false
                ),
                onRefresh = {}
            )
        }

        // Then: Verify that the success state is displayed
        composeTestRule.onNodeWithTag("breed_gallery_success")
            .assertIsDisplayed()

        // And: Verify that the gallery grid is displayed
        composeTestRule.onNodeWithTag("gallery_grid")
            .assertIsDisplayed()

        // And: Verify that all refreshed image items are displayed
        refreshedImageUrls.forEachIndexed { index, _ ->
            composeTestRule.onNodeWithTag("gallery_item_$index")
                .assertIsDisplayed()
        }
    }
}
