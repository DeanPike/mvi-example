package au.com.deanpike.listings.ui.robot

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.listings.client.model.listing.response.Project
import au.com.deanpike.listings.ui.list.ListingListScreenEvent
import au.com.deanpike.listings.ui.list.component.ProjectCard
import au.com.deanpike.listings.ui.list.component.ProjectCardTesTags.PROJECT_CARD_ADDRESS
import au.com.deanpike.listings.ui.list.component.ProjectCardTesTags.PROJECT_CARD_IMAGE
import au.com.deanpike.listings.ui.list.component.ProjectCardTesTags.PROJECT_CARD_LAYOUT
import au.com.deanpike.listings.ui.list.component.ProjectCardTesTags.PROJECT_CARD_PROJECT_NAME
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.TestRobotBase
import au.com.deanpike.uitestshared.base.TestRobotInitData
import au.com.deanpike.uitestshared.util.advanceTimeAndWait
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTextDisplayed
import au.com.deanpike.uitestshared.util.clickOn
import au.com.deanpike.uitestshared.util.disableAnimations

class ProjectCardRobot(composeRule: ComposeContentTestRule) : TestRobotBase<ProjectCardRobot, ProjectCardRobotInitData>(composeRule) {

    var projectSelectedId: Long? = null
        private set
    var propertySelectedId: Long? = null
        private set

    override fun setupComponent(data: ProjectCardRobotInitData?) = apply {
        composeRule.setContent {
            MviExampleTheme {
                ProjectCard(
                    project = data!!.project,
                    onEvent = { event ->
                        when (event) {
                            is ListingListScreenEvent.OnProjectSelected -> projectSelectedId = event.id
                            is ListingListScreenEvent.OnPropertySelected -> propertySelectedId = event.id
                            else -> {}
                        }
                    }
                )
            }
        }
        composeRule.disableAnimations()
    }

    override fun assertLayoutDisplayed() = apply {
        composeRule.advanceTimeAndWait()
        composeRule.assertTagDisplayed(PROJECT_CARD_LAYOUT)
    }

    fun assertImageDisplayed() = apply {
        composeRule.assertTagDisplayed(PROJECT_CARD_IMAGE)
    }

    fun assertProjectNameDisplayed(projectName: String) = apply {
        composeRule.assertTextDisplayed(tag = PROJECT_CARD_PROJECT_NAME, text = projectName)
    }

    fun assertAddressDisplayed(address: String) = apply {
        composeRule.assertTextDisplayed(tag = PROJECT_CARD_ADDRESS, text = address)
    }

    fun clickProjectCard() = apply {
        composeRule.clickOn(PROJECT_CARD_LAYOUT)
        composeRule.advanceTimeAndWait()
    }
}

data class ProjectCardRobotInitData(
    val project: Project
) : TestRobotInitData