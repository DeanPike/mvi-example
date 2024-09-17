package au.com.deanpike.ui.framework.robot

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithTag
import au.com.deanpike.ui.screen.list.ListingListScreenContent
import au.com.deanpike.ui.screen.list.ListingListScreenState
import au.com.deanpike.ui.screen.list.ListingListScreenTestTags.LISTING_LIST
import au.com.deanpike.ui.screen.list.ListingListScreenTestTags.LISTING_LIST_HEADING
import au.com.deanpike.ui.screen.list.component.ProjectListItemTesTags.PROJECT_LIST_ITEM_LAYOUT
import au.com.deanpike.ui.screen.list.component.PropertyListItemTesTags.PROPERTY_LIST_ITEM_LAYOUT
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.TestRobotBase
import au.com.deanpike.uitestshared.base.TestRobotInitData
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTextDisplayed
import au.com.deanpike.uitestshared.util.scrollToItemPosition

class ListingListScreenRobot(private val composeRule: ComposeContentTestRule) : TestRobotBase<ListingListScreenRobot, ListingListScreenRobotInitData>(composeRule) {
    override fun setupComponent(data: ListingListScreenRobotInitData?): ListingListScreenRobot {
        composeRule.setContent {
            MviExampleTheme {
                ListingListScreenContent(
                    state = data!!.state
                )
            }
        }

        return this
    }

    override fun assertLayoutDisplayed(): ListingListScreenRobot {
        composeRule.assertTagDisplayed(LISTING_LIST_HEADING)
        return this
    }

    fun assertHeaderText(text: String): ListingListScreenRobot {
        composeRule.assertTextDisplayed(
            tag = LISTING_LIST_HEADING,
            text = text
        )
        return this
    }

    fun assertListDisplayed(): ListingListScreenRobot {
        composeRule.assertTagDisplayed(LISTING_LIST)
        return this
    }

    fun assertProjectDisplayedAtPosition(position: Int): ListingListScreenRobot {
        composeRule.onNodeWithTag(
            testTag = LISTING_LIST,
            useUnmergedTree = true
        )
            .onChildAt(position)
            .assert(hasTestTag(PROJECT_LIST_ITEM_LAYOUT))
        return this
    }

    fun assertPropertyDisplayedAtPosition(position: Int): ListingListScreenRobot {
        composeRule.onNodeWithTag(
            useUnmergedTree = true,
            testTag = LISTING_LIST
        )
            .onChildAt(position)
            .assert(hasTestTag(PROPERTY_LIST_ITEM_LAYOUT))
        return this
    }

    fun scrollToPosition(position: Int): ListingListScreenRobot {
        composeRule.scrollToItemPosition(tag = LISTING_LIST, index = position)
        return this
    }
}

data class ListingListScreenRobotInitData(
    val state: ListingListScreenState
) : TestRobotInitData
