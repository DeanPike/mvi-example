package au.com.deanpike.listings.ui.robot

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithTag
import au.com.deanpike.listings.ui.list.ListingListScreenContent
import au.com.deanpike.listings.ui.list.ListingListScreenState
import au.com.deanpike.listings.ui.list.ListingListScreenTestTags.LISTING_LIST
import au.com.deanpike.listings.ui.list.ListingListScreenTestTags.LISTING_LIST_HEADING
import au.com.deanpike.listings.ui.list.component.ProjectCardTesTags.PROJECT_CARD_LAYOUT
import au.com.deanpike.listings.ui.list.component.PropertyCardTestTags.PROPERTY_CARD_LAYOUT
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.TestRobotBase
import au.com.deanpike.uitestshared.base.TestRobotInitData
import au.com.deanpike.uitestshared.util.advanceTimeAndWait
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTextDisplayed
import au.com.deanpike.uitestshared.util.scrollToItemPosition

class ListingListScreenRobot(composeRule: ComposeContentTestRule) : TestRobotBase<ListingListScreenRobot, ListingListScreenRobotInitData>(composeRule) {
    override fun setupComponent(data: ListingListScreenRobotInitData?) = apply {
        composeRule.setContent {
            MviExampleTheme {
                ListingListScreenContent(
                    state = data!!.state
                )
            }
        }
    }

    override fun assertLayoutDisplayed() = apply {
        composeRule.advanceTimeAndWait()
        composeRule.assertTagDisplayed(LISTING_LIST_HEADING)
    }

    fun assertHeaderText(text: String) = apply {
        composeRule.assertTextDisplayed(
            tag = LISTING_LIST_HEADING,
            text = text
        )
    }

    fun assertListDisplayed() = apply {
        composeRule.assertTagDisplayed(LISTING_LIST)
    }

    fun assertProjectDisplayedAtPosition(position: Int) = apply {
        composeRule.onNodeWithTag(
            testTag = LISTING_LIST,
            useUnmergedTree = true
        )
            .onChildAt(position)
            .assert(hasTestTag(PROJECT_CARD_LAYOUT))
    }

    fun assertPropertyDisplayedAtPosition(position: Int) = apply {
        composeRule.onNodeWithTag(
            useUnmergedTree = true,
            testTag = LISTING_LIST
        )
            .onChildAt(position)
            .assert(hasTestTag(PROPERTY_CARD_LAYOUT))
    }

    fun scrollToPosition(position: Int) = apply {
        composeRule.scrollToItemPosition(tag = LISTING_LIST, index = position)
    }
}

data class ListingListScreenRobotInitData(
    val state: ListingListScreenState
) : TestRobotInitData
