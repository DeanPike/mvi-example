package au.com.deanpike.detail.ui.framework.robot

import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithTag
import au.com.deanpike.detail.ui.R
import au.com.deanpike.detail.ui.project.ProjectChildrenComponentTestTags.PROJECT_CHILDREN
import au.com.deanpike.detail.ui.project.ProjectDetailScreenContent
import au.com.deanpike.detail.ui.project.ProjectDetailScreenState
import au.com.deanpike.detail.ui.project.ProjectDetailScreenTestTags.PROJECT_DETAILS_LAYOUT
import au.com.deanpike.detail.ui.project.ProjectDetailScreenTestTags.PROJECT_DETAIL_ADDRESS
import au.com.deanpike.detail.ui.project.ProjectDetailScreenTestTags.PROJECT_DETAIL_CLOSE
import au.com.deanpike.detail.ui.project.ProjectDetailScreenTestTags.PROJECT_DETAIL_DESCRIPTION
import au.com.deanpike.detail.ui.project.ProjectDetailScreenTestTags.PROJECT_DETAIL_HEADLINE
import au.com.deanpike.detail.ui.project.ProjectDetailScreenTestTags.PROJECT_DETAIL_NAME
import au.com.deanpike.detail.ui.project.ProjectDetailScreenTestTags.PROJECT_DETAIL_PROGRESS
import au.com.deanpike.detail.ui.project.ProjectDetailScreenTestTags.PROJECT_LAYOUT
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.TestRobotBase
import au.com.deanpike.uitestshared.base.TestRobotInitData
import au.com.deanpike.uitestshared.util.assertDrawableDisplayed
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTagDoesNotExist
import au.com.deanpike.uitestshared.util.assertTextDisplayed
import au.com.deanpike.uitestshared.util.clickOn
import au.com.deanpike.uitestshared.util.scrollToItemPosition

class ProjectDetailScreenRobot(private val composeRule: ComposeContentTestRule) : TestRobotBase<ProjectDetailScreenRobot, ProjectDetailScreenRobotInitData>(composeRule) {
    var selectedProjectChildId: Long? = null
    var closeClicked = false
    var retryClicked = false

    override fun setupComponent(data: ProjectDetailScreenRobotInitData?): ProjectDetailScreenRobot {
        selectedProjectChildId = null
        closeClicked = false

        composeRule.setContent {
            MviExampleTheme {
                ProjectDetailScreenContent(
                    state = data!!.project,
                    onCloseClicked = {
                        closeClicked = true
                    },
                    onRetryClicked = {
                        retryClicked = true
                    },
                    onProjectChildClicked = {
                        selectedProjectChildId = it
                    }
                )
            }
        }
        return this
    }

    override fun assertLayoutDisplayed(): ProjectDetailScreenRobot {
        composeRule.assertTagDisplayed(PROJECT_LAYOUT)
        return this
    }

    fun scrollProjectChildToPosition(position: Int): ProjectDetailScreenRobot {
        composeRule.scrollToItemPosition(
            tag = PROJECT_CHILDREN,
            index = position
        )
        return this
    }

    fun assertDetailsLayoutDisplayed(): ProjectDetailScreenRobot {
        composeRule.assertTagDisplayed(PROJECT_DETAILS_LAYOUT)
        return this
    }

    fun assertDetailsLayoutNotDisplayed(): ProjectDetailScreenRobot {
        composeRule.assertTagDoesNotExist(PROJECT_DETAILS_LAYOUT)
        return this
    }

    fun assertLoadingScreenDisplayed(): ProjectDetailScreenRobot {
        composeRule.assertTagDisplayed(PROJECT_DETAIL_PROGRESS)
        return this
    }

    fun assertLoadingScreenNotDisplayed(): ProjectDetailScreenRobot {
        composeRule.assertTagDoesNotExist(PROJECT_DETAIL_PROGRESS)
        return this
    }

    fun assertCloseDisplayed(): ProjectDetailScreenRobot {
        composeRule.assertDrawableDisplayed(
            tag = PROJECT_DETAIL_CLOSE,
            drawable = R.drawable.clear_24
        )
        return this
    }

    fun assertProjectName(name: String): ProjectDetailScreenRobot {
        composeRule.assertTextDisplayed(
            tag = PROJECT_DETAIL_NAME,
            text = name
        )
        return this
    }

    fun assertProjectAddress(text: String): ProjectDetailScreenRobot {
        composeRule.assertTextDisplayed(
            tag = PROJECT_DETAIL_ADDRESS,
            text = text
        )
        return this
    }

    fun assertProjectHeadline(text: String): ProjectDetailScreenRobot {
        composeRule.assertTextDisplayed(
            tag = PROJECT_DETAIL_HEADLINE,
            text = text
        )
        return this
    }

    fun assertProjectDetailDescription(description: String): ProjectDetailScreenRobot {
        composeRule
            .onNodeWithTag(PROJECT_DETAIL_DESCRIPTION)
            .assertTextContains(
                value = description,
                substring = true
            )
        return this
    }

    fun clickOnClose(): ProjectDetailScreenRobot {
        composeRule.clickOn(PROJECT_DETAIL_CLOSE)
        return this
    }
}

data class ProjectDetailScreenRobotInitData(
    val project: ProjectDetailScreenState
) : TestRobotInitData