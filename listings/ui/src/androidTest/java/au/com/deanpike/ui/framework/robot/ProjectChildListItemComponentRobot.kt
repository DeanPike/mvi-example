package au.com.deanpike.ui.framework.robot

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.commonshared.model.ListingDetails
import au.com.deanpike.ui.screen.list.component.ProjectChildListItemComponent
import au.com.deanpike.ui.screen.list.component.ProjectChildListItemComponentTestTags.PROJECT_CHILD_LIST_ITEM_LAYOUT
import au.com.deanpike.ui.screen.list.component.ProjectChildListItemComponentTestTags.PROJECT_CHILD_LIST_ITEM_LIFECYCLE
import au.com.deanpike.ui.screen.list.component.ProjectChildListItemComponentTestTags.PROJECT_CHILD_LIST_ITEM_PRICE
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.TestRobotBase
import au.com.deanpike.uitestshared.base.TestRobotInitData
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTextDisplayed
import au.com.deanpike.uitestshared.util.clickOn

class ProjectChildListItemComponentRobot(private val composeRule: ComposeContentTestRule) : TestRobotBase<ProjectChildListItemComponentRobot, ProjectChildListItemComponentRobotInitData>(composeRule) {
    var clickedId: Long? = null
        private set

    override fun setupComponent(data: ProjectChildListItemComponentRobotInitData?): ProjectChildListItemComponentRobot {
        composeRule.setContent {
            MviExampleTheme {
                ProjectChildListItemComponent(
                    id = data!!.id,
                    listingDetails = data.listingDetails,
                    lifecycleStatus = data.lifecycleStatus,
                    onProjectChildClicked = {
                        clickedId = it
                    }
                )
            }
        }

        return this
    }

    override fun assertLayoutDisplayed(): ProjectChildListItemComponentRobot {
        composeRule.assertTagDisplayed(PROJECT_CHILD_LIST_ITEM_LAYOUT)
        return this
    }

    fun assertPriceDisplayed(text: String): ProjectChildListItemComponentRobot {
        composeRule.assertTextDisplayed(
            tag = PROJECT_CHILD_LIST_ITEM_PRICE,
            text = text
        )
        return this
    }

    fun assertLifecycleDisplayed(text: String): ProjectChildListItemComponentRobot {
        composeRule.assertTextDisplayed(
            tag = PROJECT_CHILD_LIST_ITEM_LIFECYCLE,
            text = text
        )
        return this
    }

    fun clickCard(): ProjectChildListItemComponentRobot {
        composeRule.clickOn(tag = PROJECT_CHILD_LIST_ITEM_LAYOUT)
        return this
    }
}

data class ProjectChildListItemComponentRobotInitData(
    val id: Long,
    val lifecycleStatus: String?,
    val listingDetails: ListingDetails,
) : TestRobotInitData
