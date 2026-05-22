package au.com.deanpike.detail.ui.robot

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.detail.client.model.detail.ProjectChild
import au.com.deanpike.detail.ui.project.ProjectChildComponent
import au.com.deanpike.detail.ui.project.ProjectChildComponentTestTags.PROJECT_CHILD_IMAGE
import au.com.deanpike.detail.ui.project.ProjectChildComponentTestTags.PROJECT_CHILD_LAYOUT
import au.com.deanpike.detail.ui.project.ProjectChildComponentTestTags.PROJECT_CHILD_LIFECYCLE
import au.com.deanpike.detail.ui.project.ProjectChildComponentTestTags.PROJECT_CHILD_PRICE
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.TestRobotBase
import au.com.deanpike.uitestshared.base.TestRobotInitData
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTextDisplayed
import au.com.deanpike.uitestshared.util.clickOn
import org.assertj.core.api.Assertions.assertThat

class ProjectChildComponentRobot(composeRule: ComposeContentTestRule) : TestRobotBase<ProjectChildComponentRobot, ProjectChildComponentRobotInitData>(composeRule) {
    private var clickedProjectChildId: Long? = null

    override fun setupComponent(data: ProjectChildComponentRobotInitData?) = apply {
        clickedProjectChildId = null

        composeRule.setContent {
            MviExampleTheme {
                ProjectChildComponent(
                    child = ProjectChild(
                        id = 2019256252,
                        bedroomCount = 3,
                        bathroomCount = 2,
                        carSpaceCount = 1,
                        price = "Starting from $2,000,000 with extra data",
                        propertyTypeDescription = "newApartments",
                        propertyUrl = "https://www.domain.com.au/13-crown-street-wollongong-nsw-2500-2019256252",
                        propertyImage = "https://bucket-api.domain.com.au/v1/bucket/image/2019256252_1_1_240521_034448-w3000-h1875",
                        lifecycleStatus = "New Home"
                    ),
                    screenWidth = 1000,
                    onProjectChildClicked = {
                        clickedProjectChildId = it
                    }
                )
            }
        }
    }

    override fun assertLayoutDisplayed() = apply {
        composeRule.assertTagDisplayed(PROJECT_CHILD_LAYOUT)
    }

    fun assertClickedProjectChildId(id: Long) = apply {
        assertThat(clickedProjectChildId).isEqualTo(id)
    }

    fun assertProjectChildImageDisplayed() = apply {
        composeRule.assertTagDisplayed(PROJECT_CHILD_IMAGE)
    }

    fun assertLifecycle(lifecycle: String) = apply {
        composeRule.assertTextDisplayed(
            tag = PROJECT_CHILD_LIFECYCLE,
            text = lifecycle
        )
    }

    fun assertPriceDisplayed(price: String) = apply {
        composeRule.assertTextDisplayed(
            tag = PROJECT_CHILD_PRICE,
            text = price
        )
    }

    fun clickProjectChild() = apply {
        composeRule.clickOn(PROJECT_CHILD_LAYOUT)
    }
}

data class ProjectChildComponentRobotInitData(
    val child: ProjectChild
) : TestRobotInitData