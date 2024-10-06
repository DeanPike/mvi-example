package au.com.deanpike.listings.ui.framework.robot

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.listings.client.model.listing.response.Project
import au.com.deanpike.listings.ui.list.component.ProjectListItem
import au.com.deanpike.listings.ui.list.component.ProjectListItemTesTags.PROJECT_LIST_ITEM_ADDRESS
import au.com.deanpike.listings.ui.list.component.ProjectListItemTesTags.PROJECT_LIST_ITEM_BANNER_IMAGE
import au.com.deanpike.listings.ui.list.component.ProjectListItemTesTags.PROJECT_LIST_ITEM_CHILDREN
import au.com.deanpike.listings.ui.list.component.ProjectListItemTesTags.PROJECT_LIST_ITEM_CHILD_BUTTON
import au.com.deanpike.listings.ui.list.component.ProjectListItemTesTags.PROJECT_LIST_ITEM_CHILD_COUNT
import au.com.deanpike.listings.ui.list.component.ProjectListItemTesTags.PROJECT_LIST_ITEM_IMAGE
import au.com.deanpike.listings.ui.list.component.ProjectListItemTesTags.PROJECT_LIST_ITEM_LAYOUT
import au.com.deanpike.listings.ui.list.component.ProjectListItemTesTags.PROJECT_LIST_ITEM_PROJECT_NAME
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.TestRobotBase
import au.com.deanpike.uitestshared.base.TestRobotInitData
import au.com.deanpike.uitestshared.util.advanceTimeAndWait
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTextDisplayed
import au.com.deanpike.uitestshared.util.clickOn

class ProjectListItemRobot(private val composeRule: ComposeContentTestRule) : TestRobotBase<ProjectListItemRobot, ProjectListItemRobotInitData>(composeRule) {
    var clickedProjectId: Long? = null
        private set
    var clickedProjectChildId: Long? = null
        private set

    override fun setupComponent(data: ProjectListItemRobotInitData?): ProjectListItemRobot {
        composeRule.setContent {
            MviExampleTheme {
                ProjectListItem(
                    project = data!!.project,
                    onProjectClicked = {
                        clickedProjectId = it
                    },
                    onProjectChildClicked = {
                        clickedProjectChildId = it
                    }
                )
            }
        }
        return this
    }

    override fun assertLayoutDisplayed(): ProjectListItemRobot {
        composeRule.assertTagDisplayed(PROJECT_LIST_ITEM_LAYOUT)
        return this
    }

    fun assertBannerImageDisplayed(): ProjectListItemRobot {
        composeRule.assertTagDisplayed(PROJECT_LIST_ITEM_BANNER_IMAGE)
        return this
    }

    fun assertProjectImageDisplayed(): ProjectListItemRobot {
        composeRule.assertTagDisplayed(PROJECT_LIST_ITEM_IMAGE)
        return this
    }

    fun assertProjectName(text: String): ProjectListItemRobot {
        composeRule.assertTextDisplayed(
            tag = PROJECT_LIST_ITEM_PROJECT_NAME,
            text = text
        )
        return this
    }

    fun assertAddress(text: String): ProjectListItemRobot {
        composeRule.assertTextDisplayed(
            tag = PROJECT_LIST_ITEM_ADDRESS,
            text = text
        )
        return this
    }

    fun assertProjectChildrenButtonText(text: String): ProjectListItemRobot {
        composeRule.assertTextDisplayed(
            tag = PROJECT_LIST_ITEM_CHILD_COUNT,
            text = text
        )
        return this
    }

    fun clickProjectChildButton(): ProjectListItemRobot {
        composeRule.clickOn(PROJECT_LIST_ITEM_CHILD_BUTTON)
        composeRule.advanceTimeAndWait()
        return this
    }

    fun clickProject(): ProjectListItemRobot {
        composeRule.clickOn(PROJECT_LIST_ITEM_LAYOUT)
        composeRule.advanceTimeAndWait()
        return this
    }

    fun assertChildrenDisplayed(): ProjectListItemRobot {
        composeRule.assertTagDisplayed(PROJECT_LIST_ITEM_CHILDREN)
        return this
    }
}

data class ProjectListItemRobotInitData(
    val project: Project,
) : TestRobotInitData