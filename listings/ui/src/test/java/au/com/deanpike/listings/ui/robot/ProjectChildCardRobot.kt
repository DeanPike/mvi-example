package au.com.deanpike.listings.ui.robot

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.listings.client.model.listing.response.ProjectChild
import au.com.deanpike.listings.ui.list.component.ProjectChildCard
import au.com.deanpike.listings.ui.list.component.ProjectChildCardTestTags.PROJECT_CHILD_CARD_DETAILS
import au.com.deanpike.listings.ui.list.component.ProjectChildCardTestTags.PROJECT_CHILD_CARD_LAYOUT
import au.com.deanpike.listings.ui.list.component.ProjectChildCardTestTags.PROJECT_CHILD_CARD_LIFECYCLE_STATUS
import au.com.deanpike.listings.ui.list.component.ProjectChildCardTestTags.PROJECT_CHILD_CARD_PRICE
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.TestRobotBase
import au.com.deanpike.uitestshared.base.TestRobotInitData
import au.com.deanpike.uitestshared.util.advanceTimeAndWait
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTagDisplayedAtPosition
import au.com.deanpike.uitestshared.util.assertTagDoesNotExist
import au.com.deanpike.uitestshared.util.assertTextDisplayed
import au.com.deanpike.uitestshared.util.assertTextDisplayedAtPosition
import au.com.deanpike.uitestshared.util.clickOn
import au.com.deanpike.uitestshared.util.disableAnimations

class ProjectChildCardRobot(composeRule: ComposeContentTestRule) : TestRobotBase<ProjectChildCardRobot, ProjectChildCardRobotInitData>(composeRule) {

    var childClickedId: Long? = null

    override fun setupComponent(data: ProjectChildCardRobotInitData?) = apply {
        composeRule.setContent {
            MviExampleTheme {
                ProjectChildCard(
                    projectChild = data!!.projectChild,
                    onProjectChildClicked = { id ->
                        childClickedId = id
                    }
                )
            }
        }
        composeRule.disableAnimations()
    }

    override fun assertLayoutDisplayed() = apply {
        composeRule.advanceTimeAndWait()
        composeRule.assertTagDisplayed(PROJECT_CHILD_CARD_LAYOUT)
    }

    fun assertLayoutDisplayedAtPosition(position: Int) = apply {
        composeRule.advanceTimeAndWait()
        composeRule.assertTagDisplayedAtPosition(tag = PROJECT_CHILD_CARD_LAYOUT, position = position)
    }

    fun assertPriceDisplayed(price: String, position: Int? = null) = apply {
        if (position == null) {
            composeRule.assertTextDisplayed(tag = PROJECT_CHILD_CARD_PRICE, text = price)
        } else {
            composeRule.assertTextDisplayedAtPosition(
                tag = PROJECT_CHILD_CARD_PRICE,
                text = price,
                position = position
            )
        }
    }

    fun assertPriceNotDisplayed() = apply {
        composeRule.assertTagDoesNotExist(PROJECT_CHILD_CARD_PRICE)
    }

    fun assertDetailsDisplayed(details: String, position: Int? = null) = apply {
        if (position == null) {
            composeRule.assertTextDisplayed(tag = PROJECT_CHILD_CARD_DETAILS, text = details)
        } else {
            composeRule.assertTextDisplayedAtPosition(
                tag = PROJECT_CHILD_CARD_DETAILS,
                text = details,
                position = position
            )
        }
    }

    fun assertLifecycleStatusDisplayed(status: String, position: Int? = null) = apply {
        if (position == null) {
            composeRule.assertTextDisplayed(tag = PROJECT_CHILD_CARD_LIFECYCLE_STATUS, text = status)
        } else {
            composeRule.assertTextDisplayedAtPosition(
                tag = PROJECT_CHILD_CARD_LIFECYCLE_STATUS,
                text = status,
                position = position
            )
        }
    }

    fun assertLifecycleStatusNotDisplayed() = apply {
        composeRule.assertTagDoesNotExist(PROJECT_CHILD_CARD_LIFECYCLE_STATUS)
    }

    fun clickProjectChildCard() = apply {
        composeRule.clickOn(PROJECT_CHILD_CARD_LAYOUT)
        composeRule.advanceTimeAndWait()
    }
}

data class ProjectChildCardRobotInitData(
    val projectChild: ProjectChild
) : TestRobotInitData