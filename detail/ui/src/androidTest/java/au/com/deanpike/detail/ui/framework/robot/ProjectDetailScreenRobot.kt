package au.com.deanpike.detail.ui.framework.robot

import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.espresso.Espresso
import au.com.deanpike.detail.ui.project.ProjectChildrenComponentTestTags.PROJECT_CHILDREN
import au.com.deanpike.detail.ui.project.ProjectDetailScreenContent
import au.com.deanpike.detail.ui.project.ProjectDetailScreenEvent
import au.com.deanpike.detail.ui.project.ProjectDetailScreenState
import au.com.deanpike.detail.ui.project.ProjectDetailScreenTestTags.PROJECT_DETAILS_LAYOUT
import au.com.deanpike.detail.ui.project.ProjectDetailScreenTestTags.PROJECT_DETAIL_BACK_BUTTON
import au.com.deanpike.detail.ui.project.ProjectDetailScreenTestTags.PROJECT_DETAIL_DESCRIPTION
import au.com.deanpike.detail.ui.project.ProjectDetailScreenTestTags.PROJECT_DETAIL_HEADLINE
import au.com.deanpike.detail.ui.project.ProjectDetailScreenTestTags.PROJECT_DETAIL_LOADING_ADDRESS
import au.com.deanpike.detail.ui.project.ProjectDetailScreenTestTags.PROJECT_DETAIL_LOADING_TITLE
import au.com.deanpike.detail.ui.project.ProjectDetailScreenTestTags.PROJECT_DETAIL_NAME
import au.com.deanpike.detail.ui.project.ProjectDetailScreenTestTags.PROJECT_DETAIL_PROGRESS
import au.com.deanpike.detail.ui.project.ProjectDetailScreenTestTags.PROJECT_DETAIL_SUCCESS_ADDRESS
import au.com.deanpike.detail.ui.project.ProjectDetailScreenTestTags.PROJECT_LAYOUT
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.TestRobotBase
import au.com.deanpike.uitestshared.base.TestRobotInitData
import au.com.deanpike.uitestshared.util.advanceTimeAndWait
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTagDoesNotExist
import au.com.deanpike.uitestshared.util.assertTextDisplayed
import au.com.deanpike.uitestshared.util.clickOn
import au.com.deanpike.uitestshared.util.scrollToItemPosition
import au.com.deanpike.uitestshared.util.swipeUp
import au.com.deanpike.uitestshared.util.waitUntilTagExists

class ProjectDetailScreenRobot(composeRule: ComposeContentTestRule) : TestRobotBase<ProjectDetailScreenRobot, ProjectDetailScreenRobotInitData>(composeRule) {
    var selectedProjectChildId: Long? = null
    var closeClicked = false
    var event: ProjectDetailScreenEvent? = null

    override fun setupComponent(data: ProjectDetailScreenRobotInitData?): ProjectDetailScreenRobot {
        selectedProjectChildId = null
        closeClicked = false

        composeRule.setContent {
            MviExampleTheme {
                ProjectDetailScreenContent(
                    state = data!!.project,
                    loadingAddress = data.loadingAddress,
                    onEvent = {
                        event = it
                    },
                    onCloseClicked = {
                        closeClicked = true
                    },
                    onProjectChildClicked = {
                        selectedProjectChildId = it
                    }
                )
            }
        }
        composeRule.advanceTimeAndWait()
        return this
    }

    override fun assertLayoutDisplayed(): ProjectDetailScreenRobot {
        composeRule.assertTagDisplayed(PROJECT_LAYOUT)
        return this
    }

    fun waitUntilScreenDisplayed(): ProjectDetailScreenRobot {
        composeRule.advanceTimeAndWait()
        composeRule.waitUntilTagExists(PROJECT_DETAIL_NAME)
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

    fun assertProjectName(name: String): ProjectDetailScreenRobot {
        composeRule.assertTextDisplayed(
            tag = PROJECT_DETAIL_NAME,
            text = name
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

    fun swipeUp(): ProjectDetailScreenRobot {
        composeRule.swipeUp(PROJECT_LAYOUT)
        return this
    }

    fun swipeBack(): ProjectDetailScreenRobot {
        Espresso.pressBack()
        return this
    }

    fun assertLoadingTitleDisplayed(): ProjectDetailScreenRobot {
        composeRule.assertTextDisplayed(
            tag = PROJECT_DETAIL_LOADING_TITLE,
            text = "Loading data for"
        )
        return this
    }

    fun assertLoadingAddressDisplayed(address: String): ProjectDetailScreenRobot {
        composeRule.assertTextDisplayed(
            tag = PROJECT_DETAIL_LOADING_ADDRESS,
            text = address
        )
        return this
    }

    fun assertSuccessAddressDisplayed(address: String): ProjectDetailScreenRobot {
        composeRule.assertTextDisplayed(
            tag = PROJECT_DETAIL_SUCCESS_ADDRESS,
            text = address
        )
        return this
    }

    fun assertBackButtonDisplayed(): ProjectDetailScreenRobot {
        composeRule.assertTagDisplayed(PROJECT_DETAIL_BACK_BUTTON)
        return this
    }

    fun clickBack(): ProjectDetailScreenRobot {
        composeRule.clickOn(PROJECT_DETAIL_BACK_BUTTON)
        return this
    }
}

data class ProjectDetailScreenRobotInitData(
    val project: ProjectDetailScreenState,
    var loadingAddress: String
) : TestRobotInitData