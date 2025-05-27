package com.paulallan.dogs.testutil

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsDisplayed

fun SemanticsNodeInteraction.assertIsDisplayed(item: String): SemanticsNodeInteraction {
    try {
        this.assertIsDisplayed()
    } catch (e: AssertionError) {
        println("Not displayed: $item")
        throw AssertionError("Not displayed: $item", e)
    }
    return this
}