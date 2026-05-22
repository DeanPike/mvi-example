package au.com.deanpike.detail.ui.robot

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.detail.client.model.detail.ProjectChild
import au.com.deanpike.detail.ui.project.ProjectChildComponentTestTags.PROJECT_CHILD_LAYOUT
import au.com.deanpike.detail.ui.project.ProjectChildrenComponent
import au.com.deanpike.detail.ui.project.ProjectChildrenComponentTestTags.PROJECT_CHILDREN
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.TestRobotBase
import au.com.deanpike.uitestshared.base.TestRobotInitData
import au.com.deanpike.uitestshared.util.assertListingDisplayedAtPosition
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.clickOnItemAtPosition
import au.com.deanpike.uitestshared.util.scrollToItemPosition

class ProjectChildrenComponentRobot(composeRule: ComposeContentTestRule) : TestRobotBase<ProjectChildrenComponentRobot, ProjectChildrenComponentRobotInitData>(composeRule) {
    private var clickedProjectChildId: Long? = null
    override fun setupComponent(data: ProjectChildrenComponentRobotInitData?) = apply {
        clickedProjectChildId = null

        composeRule.setContent {
            MviExampleTheme {
                ProjectChildrenComponent(
                    childListings = data!!.childListings,
                    screenWidth = 1000,
                    onProjectChildClicked = {
                        clickedProjectChildId = it
                    }
                )
            }
        }
    }

    override fun assertLayoutDisplayed() = apply {
        composeRule.assertTagDisplayed(PROJECT_CHILDREN)
    }

    fun scrollToPosition(position: Int) = apply {
        composeRule.scrollToItemPosition(
            tag = PROJECT_CHILDREN,
            index = position
        )
    }

    fun assertListingDisplayedAtPosition(
        position: Int,
        listingId: Long
    ) = apply {
        composeRule.assertListingDisplayedAtPosition(
            parentTag = PROJECT_CHILDREN,
            position = position,
            childTag = PROJECT_CHILD_LAYOUT,
            listingId = listingId
        )
    }

    fun clickOnChild(position: Int) = apply {
        composeRule.clickOnItemAtPosition(
            parentTag = PROJECT_CHILDREN,
            position = position
        )
    }
}

data class ProjectChildrenComponentRobotInitData(
    val childListings: List<ProjectChild>
) : TestRobotInitData