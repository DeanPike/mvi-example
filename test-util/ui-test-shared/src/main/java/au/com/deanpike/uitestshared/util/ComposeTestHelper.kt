package au.com.deanpike.uitestshared.util

import androidx.annotation.DrawableRes
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.uishared.base.DrawablePropertyKey

fun ComposeContentTestRule.assertTextDisplayed(text: String) {
    onNodeWithText(text = text, useUnmergedTree = true).assertIsDisplayed()
}

fun ComposeContentTestRule.assertTextDisplayed(tag: String, text: String) {
    onNodeWithTag(testTag = tag, useUnmergedTree = true).assertTextEquals(text).assertIsDisplayed()
}

fun ComposeContentTestRule.assertContentDescription(tag: String, text: String) {
    onNodeWithTag(
        useUnmergedTree = true,
        testTag = tag,
    ).assertContentDescriptionEquals(text)
}

fun ComposeContentTestRule.assertTagDisplayed(tag: String) {
    onNodeWithTag(testTag = tag, useUnmergedTree = true).assertIsDisplayed()
}

fun ComposeContentTestRule.clickOnText(text: String, advanceTimeMillis: Long = 500) {
    onNodeWithText(text).performClick()
    mainClock.advanceTimeBy(advanceTimeMillis)
    waitForIdle()
}

fun ComposeContentTestRule.clickOn(tag: String, advanceTimeMillis: Long = 500) {
    onNodeWithTag(testTag = tag, useUnmergedTree = true).performClick()
    mainClock.advanceTimeBy(advanceTimeMillis)
    waitForIdle()
}

fun ComposeContentTestRule.assertTagDoesNotExist(tag: String) {
    onNodeWithTag(testTag = tag).assertDoesNotExist()
}

fun ComposeContentTestRule.writeText(tag: String, text: String) {
    onNodeWithTag(testTag = tag).performTextInput(text = text)
}

fun ComposeContentTestRule.clearText(tag: String) {
    onNodeWithTag(testTag = tag).performTextClearance()
}

fun ComposeContentTestRule.assertButtonWithTextDisplayed(tag: String, text: String) {
    onNode(matcher = hasTestTag(tag), useUnmergedTree = true)
        .onChild()
        .assert(hasText(text))
        .assertIsDisplayed()
}

fun ComposeContentTestRule.scrollTo(tag: String, advanceTimeMillis: Long = 500) {
    onNodeWithTag(tag, useUnmergedTree = true).performScrollTo()
    mainClock.advanceTimeBy(advanceTimeMillis)
}

fun ComposeContentTestRule.scrollToItemToPosition(tag: String, index: Int) {
    onNodeWithTag(tag).performScrollToIndex(index)
}

fun ComposeContentTestRule.swipeUp(tag: String) {
    onNodeWithTag(tag, useUnmergedTree = true).performTouchInput {
        this.swipeUp()
    }
    waitForIdle()
}

fun ComposeContentTestRule.swipeLeft(tag: String) {
    onNodeWithTag(tag, useUnmergedTree = true).performTouchInput {
        this.swipeLeft()
    }
    waitForIdle()
}

fun ComposeContentTestRule.swipeDown(tag: String) {
    onNodeWithTag(tag, useUnmergedTree = true).performTouchInput {
        this.swipeDown()
    }
    waitForIdle()
}

fun ComposeContentTestRule.assertIsOn(tag: String) {
    onNodeWithTag(tag, useUnmergedTree = true).assertIsOn()
}

fun ComposeContentTestRule.assertIsOff(tag: String) {
    onNodeWithTag(tag, useUnmergedTree = true).assertIsOff()
}

fun ComposeContentTestRule.assertIsSelected(tag: String) {
    onNodeWithTag(tag, useUnmergedTree = true).assertIsSelected()
}

fun ComposeContentTestRule.assertIsNotSelected(tag: String) {
    onNodeWithTag(tag, useUnmergedTree = true).assertIsNotSelected()
}

fun ComposeContentTestRule.setRangeSliderMinimumValue(tag: String, value: Float, advanceTimeMillis: Long = 500) {
    onNode(
        useUnmergedTree = true,
        matcher = hasContentDescription("Range start")
            and
            hasParent(
                hasTestTag(tag)
            )
    ).assertExists()
        .performSemanticsAction(SemanticsActions.SetProgress) {
            it.invoke(value)
        }

    mainClock.advanceTimeBy(advanceTimeMillis)
}

fun ComposeContentTestRule.setRangeSliderMaximumValue(tag: String, value: Float, advanceTimeMillis: Long = 500) {
    onNode(
        useUnmergedTree = true,
        matcher = hasContentDescription("Range end")
            and
            hasParent(
                hasTestTag(tag)
            )
    ).assertExists()
        .performSemanticsAction(SemanticsActions.SetProgress) {
            it.invoke(value)
        }

    mainClock.advanceTimeBy(advanceTimeMillis)
}

fun ComposeContentTestRule.assertRangeSliderMinimumValue(tag: String, value: Float, advanceTimeMillis: Long = 500) {
    onNode(
        useUnmergedTree = true,
        matcher = hasTestTag(tag)
            and hasAnyChild(
            SemanticsMatcher(description = "Range slider minimum value = $value") {
                val rangeInfo = it.config.getOrNull(SemanticsProperties.ProgressBarRangeInfo)

                val minimum = rangeInfo!!.range.start
                minimum == value
            }
        )
    ).assertExists()
}

fun ComposeContentTestRule.assertRangeSliderMaximumValue(tag: String, value: Float, advanceTimeMillis: Long = 500) {
    onNode(
        useUnmergedTree = true,
        matcher = hasTestTag(tag)
            and hasAnyChild(
            SemanticsMatcher(description = "Range slider maximum value = $value") {
                val rangeInfo = it.config.getOrNull(SemanticsProperties.ProgressBarRangeInfo)

                val maximum = rangeInfo!!.current
                maximum == value
            }
        )
    ).assertExists()
}

fun ComposeContentTestRule.assertRangeSliderStepCount(tag: String, stepCount: Float) {
    onNode(
        useUnmergedTree = true,
        matcher = hasTestTag(tag)
            and hasAnyChild(
            SemanticsMatcher(description = "Range slider step count = $stepCount") {
                val rangeInfo = it.config.getOrNull(SemanticsProperties.ProgressBarRangeInfo)
                rangeInfo!!.range.endInclusive == stepCount
            }
        )
    ).assertExists()
}

fun ComposeContentTestRule.assertTextInParent(
    parentTag: String,
    tag: String,
    text: String
) {
    onNodeWithTag(testTag = parentTag, useUnmergedTree = true)
        .assert(
            hasAnyDescendant(
                hasTestTag(tag) and hasText(text)
            )
        )
}

fun ComposeContentTestRule.assertTagInParent(
    parentTag: String,
    tag: String
) {
    onNodeWithTag(testTag = parentTag, useUnmergedTree = true)
        .assert(
            hasAnyDescendant(
                hasTestTag(tag)
            )
        )
}

fun ComposeContentTestRule.clickTagInParent(
    parentTag: String,
    tag: String
) {
    val childNode = onNodeWithTag(testTag = tag, useUnmergedTree = true)
    val buttonNode = childNode.assert(hasParent(hasTestTag(parentTag)))
    buttonNode.performClick()
}

@OptIn(ExperimentalTestApi::class)
fun ComposeContentTestRule.waitUntilTagExists(tag: String, timeout: Long = 1000) {
    waitUntilAtLeastOneExists(hasTestTag(tag), timeout)
}

fun ComposeContentTestRule.assertDrawableDisplayed(
    tag: String,
    @DrawableRes drawable: Int
) {
    onNode(
        useUnmergedTree = true,
        matcher = hasTestTag(tag) and
            SemanticsMatcher.expectValue(key = DrawablePropertyKey, expectedValue = drawable)
    ).assertIsDisplayed()
}