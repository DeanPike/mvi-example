package au.com.deanpike.detail.ui.framework.robot

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

class ProjectChildrenComponentRobot(private val composeRule: ComposeContentTestRule) : TestRobotBase<ProjectChildrenComponentRobot, ProjectChildrenComponentRobotInitData>(composeRule) {
    private var clickedProjectChildId: Long? = null
    override fun setupComponent(data: ProjectChildrenComponentRobotInitData?): ProjectChildrenComponentRobot {
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
        return this
    }

    override fun assertLayoutDisplayed(): ProjectChildrenComponentRobot {
        composeRule.assertTagDisplayed(PROJECT_CHILDREN)
        return this
    }

    fun scrollToPosition(position: Int): ProjectChildrenComponentRobot {
        composeRule.scrollToItemPosition(
            tag = PROJECT_CHILDREN,
            index = position
        )
        return this
    }

    fun assertListingDisplayedAtPosition(
        position: Int,
        listingId: Long
    ): ProjectChildrenComponentRobot {
        composeRule.assertListingDisplayedAtPosition(
            parentTag = PROJECT_CHILDREN,
            position = position,
            childTag = PROJECT_CHILD_LAYOUT,
            listingId = listingId
        )
        return this
    }

    fun clickOnChild(position: Int): ProjectChildrenComponentRobot {
        composeRule.clickOnItemAtPosition(
            parentTag = PROJECT_CHILDREN,
            position = position
        )
        return this
    }
}

data class ProjectChildrenComponentRobotInitData(
    val childListings: List<ProjectChild>
) : TestRobotInitData